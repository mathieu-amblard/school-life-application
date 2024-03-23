package amb.mat.school.life.studentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @GetMapping
    public void get() {
        System.out.println("I have been called with user " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PreAuthorize("hasAuthority('SCOPE_student.write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateStudentCommand command) {
        System.out.println("I have been called with command " + command);
    }
}
