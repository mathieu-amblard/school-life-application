package amb.mat.school.life.studentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @PreAuthorize("hasAuthority('SCOPE_student.write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateStudentCommand command) {
        System.out.println("I have been called with command " + command);
    }
}
