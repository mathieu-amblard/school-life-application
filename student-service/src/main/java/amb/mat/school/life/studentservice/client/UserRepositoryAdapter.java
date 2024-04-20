package amb.mat.school.life.studentservice.client;

import amb.mat.school.life.studentservice.domain.user.Password;
import amb.mat.school.life.studentservice.domain.user.User;
import amb.mat.school.life.studentservice.domain.user.UserRepositoryPort;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserClient userClient;

    public UserRepositoryAdapter(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void putUser(User user, Password password) {
        userClient.putUser(
                user.username().value(),
                new UserDto(user.emailAddress().value(), password.value())
        );
    }
}
