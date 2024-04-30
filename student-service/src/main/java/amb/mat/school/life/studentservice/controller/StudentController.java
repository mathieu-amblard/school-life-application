package amb.mat.school.life.studentservice.controller;

import amb.mat.school.life.studentservice.application.StudentApplicationService;
import amb.mat.school.life.studentservice.application.command.CreateStudentAndUserCommand;
import amb.mat.school.life.studentservice.controller.dto.CreateStudentCommandDto;
import amb.mat.school.life.studentservice.controller.dto.PatchStudentCommandDto;
import amb.mat.school.life.studentservice.controller.dto.StudentDto;
import amb.mat.school.life.studentservice.domain.student.*;
import amb.mat.school.life.studentservice.domain.student.command.DeleteStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import amb.mat.school.life.studentservice.domain.user.EmailAddress;
import amb.mat.school.life.studentservice.domain.user.Password;
import amb.mat.school.life.studentservice.domain.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentController.class);

    private final StudentApplicationService studentApplicationService;

    public StudentController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    @PreAuthorize("hasRole('student')")
    @GetMapping("/whoami")
    public String getWhoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        LOG.info("I have been called with user {} and authorities {}", principal.getSubject(), authentication.getAuthorities());
        return principal.getSubject();
    }

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
        StudentDto studentDto = new StudentDto(
                student.studentId().value(),
                student.username().value(),
                // Ideally we should retrieve if from the user bounded context
                // but this value must not be altered (except for formatting, i.e. toLowercase, trim, ...)
                command.emailAddress(),
                student.lastname().value(),
                student.firstname().value(),
                student.birthdate().value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(studentDto);
    }

    @PreAuthorize("hasRole('admin')")
    @PatchMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String studentId, @RequestBody PatchStudentCommandDto command) {
        UpdateStudentCommand updateStudentCommand = new UpdateStudentCommand(
                new StudentId(studentId),
                Optional.ofNullable(command.lastname()).map(Lastname::new).orElse(null),
                Optional.ofNullable(command.firstname()).map(Firstname::new).orElse(null),
                Optional.ofNullable(command.birthdate()).map(Birthdate::new).orElse(null)
        );
        studentApplicationService.updateStudent(updateStudentCommand);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String studentId) {
        studentApplicationService.deleteStudent(new DeleteStudentCommand(new StudentId(studentId)));
    }
}
