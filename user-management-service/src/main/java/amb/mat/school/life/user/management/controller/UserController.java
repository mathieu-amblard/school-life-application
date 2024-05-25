package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.PutUserCommandDto;
import amb.mat.school.life.user.management.controller.dto.UserDto;
import amb.mat.school.life.user.management.domain.*;
import amb.mat.school.life.user.management.domain.command.CreateUserCommand;
import amb.mat.school.life.user.management.domain.command.DeleteUserCommand;
import amb.mat.school.life.user.management.domain.command.UpdateUserCommand;
import amb.mat.school.life.user.management.domain.query.FindUserQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @Operation(summary = "Get the authenticated user")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/whoami")
    public UserDto getWhoAmI() {
        return userDtoMapper.mapToDto(userService.getMyUser());
    }

    @Operation(summary = "Get an existing user")
    @PreAuthorize("hasRole('admin') or @userService.isOwnedBy(#username, authentication.name)  or #username == authentication.name")
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        return userService.find(new FindUserQuery(new Username(username)))
                .map(userDtoMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing user")
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{username}") // Idempotency, simplify error handling on consumers (retry for instance)
    public ResponseEntity<Void> putUser(@PathVariable String username, @RequestBody PutUserCommandDto commandDto) {
        if (userService.find(new FindUserQuery(new Username(username))).isPresent()) {
            UpdateUserCommand command = new UpdateUserCommand(
                    new Username(username),
                    new Password(commandDto.password()),
                    new EmailAddress(commandDto.emailAddress())
            );
            userService.updateUser(command);
        } else {
            CreateUserCommand command = new CreateUserCommand(
                    new Username(username),
                    new Password(commandDto.password()),
                    new EmailAddress(commandDto.emailAddress()),
                    commandDto.roles().stream().map(Role::of).collect(Collectors.toSet()),
                    new Username(SecurityContextHolder.getContext().getAuthentication().getName())
            );
            userService.createUser(command);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Delete an existing user")
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(new DeleteUserCommand(new Username(username)));
    }
}
