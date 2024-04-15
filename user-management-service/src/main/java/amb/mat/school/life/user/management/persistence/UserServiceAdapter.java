package amb.mat.school.life.user.management.persistence;

import amb.mat.school.life.user.management.domain.*;
import amb.mat.school.life.user.management.domain.command.CreateUserCommand;
import amb.mat.school.life.user.management.domain.command.UpdateUserCommand;
import amb.mat.school.life.user.management.domain.query.FindUserQuery;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceAdapter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Username getMyUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Username(username);
    }

    @Override
    @Transactional(readOnly = true)
    public User getMyUser() {
        return userRepository.findByUserName(getMyUsername())
                .orElseThrow(() -> new IllegalStateException("the authenticated user must exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwnedBy(IsOwnedByQuery query) {
        return userRepository.existsOwnerRelationshipBetween(query.username(), query.owner());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> find(FindUserQuery query) {
        return userRepository.findByUserName(query.username());
    }

    @Override
    @Transactional
    public User createUser(CreateUserCommand command) {
        User user = new User(
                command.username(),
                command.emailAddress(),
                command.roles(),
                command.owner()
        );
        EncodedPassword encodedPassword = passwordEncoder.encode(command.password());
        return userRepository.put(user, encodedPassword);
    }

    @Override
    @Transactional
    public User updateUser(UpdateUserCommand command) {
        User user = userRepository.findByUserName(command.username())
                .orElseThrow(() -> new NoSuchElementException("user not found with username %s".formatted(command.username().value())));
        if (command.requiresUpdate(user.emailAddress())) {
            user.updateEmailAddress(command.emailAddress());
            EncodedPassword encodedPassword = Optional.ofNullable(command.password()).map(passwordEncoder::encode).orElse(null);
            return userRepository.put(user, encodedPassword);
        } else {
            return user;
        }
    }
}
