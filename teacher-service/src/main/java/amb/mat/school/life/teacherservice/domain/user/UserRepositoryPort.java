package amb.mat.school.life.teacherservice.domain.user;

import java.util.Optional;

public interface UserRepositoryPort {

    Optional<User> get(Username username);

    void put(User user, Password password);

    void delete(Username username);
}
