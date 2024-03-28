package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.CreateUserAccountCommandDto;
import amb.mat.school.life.user.management.controller.dto.UserAccountDto;
import amb.mat.school.life.user.management.domain.UserAccountService;
import amb.mat.school.life.user.management.domain.Username;
import amb.mat.school.life.user.management.domain.query.FindUserAccountQuery;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("hasRole('admin') or @userAccountService.isOwnedBy(#username, authentication.name)  or #username == authentication.name")
    public ResponseEntity<UserAccountDto> getUserAccount(@PathVariable String username) {
        return userAccountService.find(new FindUserAccountQuery(new Username(username)))
                .map(userAccountDtoMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('teacher')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody CreateUserAccountCommandDto command) {
        System.out.println("I have been called with command " + command);
    }
}
