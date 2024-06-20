package amb.mat.school.life.teacher.client;

import amb.mat.school.life.teacher.domain.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryAdapter.class);

    private final UserClient userClient;

    public UserRepositoryAdapter(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public Optional<User> get(Username username) {
        return Optional.ofNullable(username)
                .map(name -> {
                    try {
                        return userClient.get(name.value());
                    } catch (HttpClientErrorException.NotFound exception) {
                        LOG.info("user {} not found", username);
                        return null;
                    }
                })
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

    @Override
    public void delete(Username username) {
        userClient.delete(username.value());
    }
}
