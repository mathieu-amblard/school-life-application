package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.domain.UserManagementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class UserAccountController {

    private final UserManagementService userManagementService;

    public UserAccountController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('admin') or @userManagementService.isOwner(#username)  or #username == authentication.principal.username")
    public void getMyAccount(@PathVariable String username) {
        System.out.println("I have been called with user " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('teacher')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody CreateUserAccountCommand command) {
        System.out.println("I have been called with command " + command);
    }
}
