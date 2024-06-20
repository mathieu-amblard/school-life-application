package amb.mat.school.life.teacher.controller;

import amb.mat.school.life.teacher.application.TeacherApplicationService;
import amb.mat.school.life.teacher.application.command.CreateTeacherAndUserCommand;
import amb.mat.school.life.teacher.controller.dto.CreateTeacherCommandDto;
import amb.mat.school.life.teacher.controller.dto.PatchTeacherCommandDto;
import amb.mat.school.life.teacher.controller.dto.TeacherDto;
import amb.mat.school.life.teacher.domain.teacher.Teacher;
import amb.mat.school.life.teacher.domain.teacher.command.DeleteTeacherCommand;
import amb.mat.school.life.teacher.domain.teacher.command.UpdateTeacherCommand;
import amb.mat.school.life.teacher.domain.teacher.query.FindAllTeachersQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherApplicationService teacherApplicationService;

    public TeacherController(TeacherApplicationService teacherApplicationService) {
        this.teacherApplicationService = teacherApplicationService;
    }

    @Operation(summary = "Get the authenticated teacher username")
    @PreAuthorize("hasRole('teacher')")
    @GetMapping("/whoami")
    public String getWhoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        LOG.info("I have been called with user {} and authorities {}", principal.getSubject(), authentication.getAuthorities());
        return principal.getSubject();
    }

    @Operation(summary = "Get all teachers")
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        List<TeacherDto> teacherDtos = teacherApplicationService.getTeachers(new FindAllTeachersQuery())
                .stream()
                .map(teacher -> mapToDto(teacher, null))
                .toList();
        return ResponseEntity.ok(teacherDtos);
    }

    @Operation(summary = "Create a new teacher")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(
                            """
                                    {
                                      "username": "teacher123",
                                      "password": "Teacher123$",
                                      "emailAddress": "eduardo.burnett@email.com",
                                      "lastname": "Burnett",
                                      "firstname": "Eduardo",
                                      "resume": "Summary of Qualifications..."
                                    }
                                    """
                    )
            )
    )
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeacherDto> create(@RequestBody CreateTeacherCommandDto body) {
        CreateTeacherAndUserCommand createTeacherAndUserCommand = body.getCommand();
        Teacher teacher = teacherApplicationService.createTeacher(createTeacherAndUserCommand);
        TeacherDto teacherDto = mapToDto(teacher, body.emailAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherDto);
    }

    @Operation(summary = "Patch an existing teacher")
    @Parameters(
            @Parameter(
                    name = "identifier",
                    in = ParameterIn.PATH,
                    required = true,
                    examples = {
                            @ExampleObject(
                                    name = "with_teacher_id",
                                    value = "11111111-1111-1111-1111-111111111111"
                            ),
                            @ExampleObject(
                                    name = "with_username",
                                    value = "teacher123"
                            )
                    }
            )
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(
                            """
                                    {
                                       "lastname": "Maddox",
                                       "firstname": "Latisha",
                                       "resume": "Core Qualifications..."
                                    }
                                    """
                    )
            )
    )
    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/{identifier}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String identifier, @RequestBody PatchTeacherCommandDto command) {
        UpdateTeacherCommand updateTeacherCommand = command.getCommand(identifier);
        teacherApplicationService.updateTeacher(updateTeacherCommand);
    }

    @Operation(summary = "Delete an existing teacher")
    @Parameters(
            @Parameter(
                    name = "identifier",
                    in = ParameterIn.PATH,
                    required = true,
                    examples = {
                            @ExampleObject(
                                    name = "with_teacher_id",
                                    value = "11111111-1111-1111-1111-111111111111"
                            ),
                            @ExampleObject(
                                    name = "with_username",
                                    value = "teacher123"
                            )
                    }
            )
    )
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{identifier}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String identifier) {
        teacherApplicationService.deleteTeacher(new DeleteTeacherCommand(identifier));
    }

    // TODO put in a dedicated mapper
    private static TeacherDto mapToDto(Teacher teacher, String emailAddress) {
        return new TeacherDto(
                teacher.teacherId().value(),
                teacher.username().value(),
                // Ideally we should retrieve if from the user bounded context
                // but this value must not be altered (except for formatting, i.e. toLowercase, trim, ...)
                emailAddress,
                teacher.lastname().value(),
                teacher.firstname().value(),
                teacher.resume().value()
        );
    }
}
