package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.CreateUserAccountCommandDto;
import amb.mat.school.life.user.management.controller.dto.UserAccountDto;
import amb.mat.school.life.user.management.domain.UserAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-accounts")
@SecurityRequirement(name = "Bearer Authentication")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final UserAccountDtoMapper userAccountDtoMapper;

    public UserAccountController(UserAccountService userAccountService, UserAccountDtoMapper userAccountDtoMapper) {
        this.userAccountService = userAccountService;
        this.userAccountDtoMapper = userAccountDtoMapper;
    }

    @GetMapping("/self")
    @PreAuthorize("isAuthenticated()")
    public UserAccountDto getMyAccount() {
        return userAccountDtoMapper.mapToDto(userAccountService.getMyAccount());
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('admin') or @userAccountService.isOwnedBy(authentication.principal.username, #username)  or #username == authentication.principal.username")
    public void getUserAccount(@PathVariable String username) {
        System.out.println("I have been called with username " + username);
    }

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('teacher')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody CreateUserAccountCommandDto command) {
        System.out.println("I have been called with command " + command);
    }
}
