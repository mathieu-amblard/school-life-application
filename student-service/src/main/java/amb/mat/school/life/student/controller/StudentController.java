package amb.mat.school.life.student.controller;

import amb.mat.school.life.student.domain.student.Firstname;
import amb.mat.school.life.student.application.StudentApplicationService;
import amb.mat.school.life.student.application.command.CreateStudentAndUserCommand;
import amb.mat.school.life.student.controller.dto.CreateStudentCommandDto;
import amb.mat.school.life.student.controller.dto.PatchStudentCommandDto;
import amb.mat.school.life.student.controller.dto.StudentDto;
import amb.mat.school.life.student.domain.student.Birthdate;
import amb.mat.school.life.student.domain.student.Lastname;
import amb.mat.school.life.student.domain.student.Student;
import amb.mat.school.life.student.domain.student.command.DeleteStudentCommand;
import amb.mat.school.life.student.domain.student.command.UpdateStudentCommand;
import amb.mat.school.life.student.domain.student.query.FindAllStudentsQuery;
import amb.mat.school.life.student.domain.user.EmailAddress;
import amb.mat.school.life.student.domain.user.Password;
import amb.mat.school.life.student.domain.user.Username;
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
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    private final StudentApplicationService studentApplicationService;

    public StudentController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    @Operation(summary = "Get the authenticated student username")
    @PreAuthorize("hasRole('student')")
    @GetMapping("/whoami")
    public String getWhoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        LOG.info("I have been called with user {} and authorities {}", principal.getSubject(), authentication.getAuthorities());
        return principal.getSubject();
    }

    @Operation(summary = "Get all students")
    @PreAuthorize("hasRole('admin')")
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtos = studentApplicationService.getStudents(new FindAllStudentsQuery())
                .stream()
                .map(student -> mapToDto(student, null))
                .toList();
        return ResponseEntity.ok(studentDtos);
    }

    @Operation(summary = "Create a new student")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    examples = @ExampleObject(
                            """
                                    {
                                      "username": "student123",
                                      "password": "Student123$",
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
    public ResponseEntity<StudentDto> create(@RequestBody CreateStudentCommandDto command) {
        CreateStudentAndUserCommand createStudentAndUserCommand = new CreateStudentAndUserCommand(
                new Username(command.username()),
                new Password(command.password()),
                new EmailAddress(command.emailAddress()),
                new Lastname(command.lastname()),
                new Firstname(command.firstname()),
                new Birthdate(command.birthdate())
        );
        Student student = studentApplicationService.createStudent(createStudentAndUserCommand);
        StudentDto studentDto = mapToDto(student, command.emailAddress());
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto);
    }

    @Operation(summary = "Patch an existing student")
    @Parameters(
            @Parameter(
                    name = "identifier",
                    in = ParameterIn.PATH,
                    required = true,
                    examples = {
                            @ExampleObject(
                                    name = "with_student_id",
                                    value = "11111111-1111-1111-1111-111111111111"
                            ),
                            @ExampleObject(
                                    name = "with_username",
                                    value = "student123"
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
    public void update(@PathVariable String identifier, @RequestBody PatchStudentCommandDto command) {
        UpdateStudentCommand updateStudentCommand = new UpdateStudentCommand(
                identifier,
                command.lastname(),
                command.firstname(),
                command.birthdate()
        );
        studentApplicationService.updateStudent(updateStudentCommand);
    }

    @Operation(summary = "Delete an existing student")
    @Parameters(
            @Parameter(
                    name = "identifier",
                    in = ParameterIn.PATH,
                    required = true,
                    examples = {
                            @ExampleObject(
                                    name = "with_student_id",
                                    value = "11111111-1111-1111-1111-111111111111"
                            ),
                            @ExampleObject(
                                    name = "with_username",
                                    value = "student123"
                            )
                    }
            )
    )
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{identifier}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String identifier) {
        studentApplicationService.deleteStudent(new DeleteStudentCommand(identifier));
    }

    // TODO put in a dedicated mapper
    private static StudentDto mapToDto(Student student, String emailAddress) {
        return new StudentDto(
                student.studentId().value(),
                student.username().value(),
                // Ideally we should retrieve if from the user bounded context
                // but this value must not be altered (except for formatting, i.e. toLowercase, trim, ...)
                emailAddress,
                student.lastname().value(),
                student.firstname().value(),
                student.birthdate().value()
        );
    }
}
