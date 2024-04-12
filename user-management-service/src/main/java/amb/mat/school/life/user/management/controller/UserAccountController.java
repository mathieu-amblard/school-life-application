package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.PutUserAccountCommandDto;
import amb.mat.school.life.user.management.controller.dto.UserAccountDto;
import amb.mat.school.life.user.management.domain.*;
import amb.mat.school.life.user.management.domain.command.CreateUserAccountCommand;
import amb.mat.school.life.user.management.domain.query.FindUserAccountQuery;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    // TODO manage role
    // admin can create teacher and students
    // teacher can create admin only
    @PutMapping("/{username}")
    // @PreAuthorize("hasRole('admin') or hasRole('teacher')")
    public ResponseEntity<Void> putAccount(@PathVariable String username, @RequestBody PutUserAccountCommandDto commandDto) {
        if (userAccountService.find(new FindUserAccountQuery(new Username(username))).isPresent()) {
            CreateUserAccountCommand command = new CreateUserAccountCommand(
                    new Username(username),
                    new Password(commandDto.password()),
                    new EmailAddress(commandDto.emailAddress()),
                    commandDto.roles().stream().map(Role::of).collect(Collectors.toSet()),
                    new Username(SecurityContextHolder.getContext().getAuthentication().getName())
            );
            userAccountService.createAccount(command);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // TODO cannot update roles
            // TODO only the owner or the user itself can update an account
            // Put @PreAuthorize on the service ?
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
