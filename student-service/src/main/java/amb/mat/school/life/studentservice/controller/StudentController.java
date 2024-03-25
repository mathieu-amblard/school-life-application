package amb.mat.school.life.studentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    // TODO to manage existing roles : https://www.baeldung.com/spring-security-oauth-principal-authorities-extractor

    @PreAuthorize("hasAuthority('SCOPE_student.read')")
    @GetMapping
    public String get() {
        System.out.println("I have been called with user " + ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClaims());
        return "Hello";
    }

    @PreAuthorize("hasAuthority('SCOPE_student.write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateStudentCommand command) {
        System.out.println("I have been called with command " + command);
    }
}
