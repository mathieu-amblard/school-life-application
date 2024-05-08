package amb.mat.school.life.user.management.domain;

import amb.mat.school.life.user.management.domain.command.CreateUserCommand;
import amb.mat.school.life.user.management.domain.command.DeleteUserCommand;
import amb.mat.school.life.user.management.domain.command.UpdateUserCommand;
import amb.mat.school.life.user.management.domain.query.FindUserQuery;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public UserService(UserRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get the {@link Username} of the connected user
     *
     * @return the {@link Username} of the connected user
     */
    public Username getMyUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Username(username);
    }

    /**
     * Get the {@link User} of the connected user
     *
     * @return the {@link User} of the connected user
     */
    @Transactional(readOnly = true)
    public User getMyUser() {
        return userRepository.findByUserName(getMyUsername())
                .orElseThrow(() -> new IllegalStateException("the authenticated user must exist"));
    }

    /**
     * Indicate if the user identified by username is owned by the specified owner
     *
     * @param username the username of the user
     * @param owner    the username of the owner
     * @return {@code true} if the user is owned by the owner, {@code false} otherwise
     */
    @Transactional(readOnly = true)
    public boolean isOwnedBy(Username username, Username owner) {
        return isOwnedBy(new IsOwnedByQuery(username, owner));
    }

    /**
     * Indicate if the user identified by username is owned by the specified owner
     *
     * @param query the {@link IsOwnedByQuery} to use
     * @return {@code true} if the user is owned by the owner, {@code false} otherwise
     */
    @Transactional(readOnly = true)
    public boolean isOwnedBy(IsOwnedByQuery query) {
        return userRepository.existsOwnerRelationshipBetween(query.username(), query.owner());
    }

    /**
     * Find a user identified by the specified username
     *
     * @param query the {@link FindUserQuery} to use
     * @return the user found, empty otherwise
     */
    @Transactional(readOnly = true)
    public Optional<User> find(FindUserQuery query) {
        return userRepository.findByUserName(query.username());
    }

    /**
     * Create a new user
     *
     * @param command the {@link CreateUserCommand} to use
     */
    @Transactional
    public void createUser(CreateUserCommand command) {
        User user = new User(
                command.username(),
                command.emailAddress(),
                command.roles(),
                command.owner()
        );
        EncodedPassword encodedPassword = passwordEncoder.encode(command.password());
        userRepository.put(user, encodedPassword);
    }

    /**
     * Update an existing user
     *
     * @param command the {@link UpdateUserCommand} to use
     */
    @Transactional
    public void updateUser(UpdateUserCommand command) {
        User user = userRepository.findByUserName(command.username())
                .orElseThrow(() -> new NoSuchElementException("user not found with username %s".formatted(command.username().value())));
        if (command.requiresUpdate(user.emailAddress())) {
            user.updateEmailAddress(command.emailAddress());
            EncodedPassword encodedPassword = Optional.ofNullable(command.password()).map(passwordEncoder::encode).orElse(null);
            userRepository.put(user, encodedPassword);
        }
    }

    /**
     * Delete an existing user
     *
     * @param command the {@link DeleteUserCommand} to use
     */
    @Transactional
    public void deleteUser(DeleteUserCommand command) {
        userRepository.remove(command.username());
    }
}
