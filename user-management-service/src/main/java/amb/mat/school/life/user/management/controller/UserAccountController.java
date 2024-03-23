package amb.mat.school.life.user.management.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class UserAccountController {

    @GetMapping
    public void get() {
        System.out.println("I have been called with user " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateUserAccountCommand command) {
        System.out.println("I have been called with command " + command);
    }
}
