package amb.mat.school.life.studentservice.controller;

import amb.mat.school.life.studentservice.application.StudentApplicationService;
import amb.mat.school.life.studentservice.application.command.CreateStudentAndUserCommand;
import amb.mat.school.life.studentservice.controller.dto.CreateStudentCommandDto;
import amb.mat.school.life.studentservice.controller.dto.StudentDto;
import amb.mat.school.life.studentservice.controller.dto.UpdatePartiallyStudentCommandDto;
import amb.mat.school.life.studentservice.domain.student.*;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import amb.mat.school.life.studentservice.domain.user.EmailAddress;
import amb.mat.school.life.studentservice.domain.user.Password;
import amb.mat.school.life.studentservice.domain.user.Username;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentApplicationService studentApplicationService;

    public StudentController(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String get() {
        System.out.println("I have been called with user " + ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims());
        System.out.println("I have been called with user + authorities " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "Hello";
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
    public void update(@PathVariable String studentId, @RequestBody UpdatePartiallyStudentCommandDto command) {
        UpdateStudentCommand updateStudentCommand = new UpdateStudentCommand(
                new StudentId(studentId),
                Optional.ofNullable(command.firstname()).map(Lastname::new).orElse(null),
                Optional.ofNullable(command.firstname()).map(Firstname::new).orElse(null),
                Optional.ofNullable(command.birthdate()).map(Birthdate::new).orElse(null)
        );
        studentApplicationService.updateStudent(updateStudentCommand);
    }
}
