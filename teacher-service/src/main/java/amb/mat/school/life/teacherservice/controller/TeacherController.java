package amb.mat.school.life.teacherservice.controller;

import amb.mat.school.life.teacherservice.application.TeacherApplicationService;
import amb.mat.school.life.teacherservice.application.command.CreateTeacherAndUserCommand;
import amb.mat.school.life.teacherservice.controller.dto.CreateTeacherCommandDto;
import amb.mat.school.life.teacherservice.controller.dto.PatchTeacherCommandDto;
import amb.mat.school.life.teacherservice.controller.dto.TeacherDto;
import amb.mat.school.life.teacherservice.domain.teacher.Birthdate;
import amb.mat.school.life.teacherservice.domain.teacher.Firstname;
import amb.mat.school.life.teacherservice.domain.teacher.Lastname;
import amb.mat.school.life.teacherservice.domain.teacher.Teacher;
import amb.mat.school.life.teacherservice.domain.teacher.command.DeleteTeacherCommand;
import amb.mat.school.life.teacherservice.domain.teacher.command.UpdateTeacherCommand;
import amb.mat.school.life.teacherservice.domain.teacher.query.FindAllTeachersQuery;
import amb.mat.school.life.teacherservice.domain.user.EmailAddress;
import amb.mat.school.life.teacherservice.domain.user.Password;
import amb.mat.school.life.teacherservice.domain.user.Username;
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
                                      "emailAddress": "jamison.rocha@email.com",
                                      "lastname": "Rocha",
                                      "firstname": "Jamison",
                                      "birthdate": "2020-05-01"
                                    }
                                    """
                    )
            )
    )
    @PreAuthorize("hasRole('admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeacherDto> create(@RequestBody CreateTeacherCommandDto command) {
        CreateTeacherAndUserCommand createTeacherAndUserCommand = new CreateTeacherAndUserCommand(
                new Username(command.username()),
                new Password(command.password()),
                new EmailAddress(command.emailAddress()),
                new Lastname(command.lastname()),
                new Firstname(command.firstname()),
                new Birthdate(command.birthdate())
        );
        Teacher teacher = teacherApplicationService.createTeacher(createTeacherAndUserCommand);
        TeacherDto teacherDto = mapToDto(teacher, command.emailAddress());
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
                                       "lastname": "Middleton",
                                       "firstname": "Ericka",
                                       "birthdate": "2019-03-20"
                                    }
                                    """
                    )
            )
    )
    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/{identifier}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String identifier, @RequestBody PatchTeacherCommandDto command) {
        UpdateTeacherCommand updateTeacherCommand = new UpdateTeacherCommand(
                identifier,
                command.lastname(),
                command.firstname(),
                command.birthdate()
        );
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
                teacher.birthdate().value()
        );
    }
}
