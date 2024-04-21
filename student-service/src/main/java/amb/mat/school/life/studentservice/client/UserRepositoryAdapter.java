package amb.mat.school.life.studentservice.client;

import amb.mat.school.life.studentservice.domain.user.*;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserClient userClient;

    public UserRepositoryAdapter(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public Optional<User> get(Username username) {
        return Optional.ofNullable(username)
                .flatMap(name -> userClient.get(name.value()))
                .map(userDto -> new User(
                        username,
                        new EmailAddress(userDto.emailAddress())
                ));
    }

    @Override
    public void put(User user, Password password) {
        userClient.put(
                user.username().value(),
                new UserDto(user.emailAddress().value(), password.value())
        );
    }
}
