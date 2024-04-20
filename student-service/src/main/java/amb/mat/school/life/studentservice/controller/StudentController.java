package amb.mat.school.life.studentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

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
    public void create(@RequestBody PutStudentCommandDto command) {
        System.out.println("I have been called with command " + command);
    }
}
