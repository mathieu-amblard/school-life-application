package amb.mat.school.life.studentservice.domain.user;

public interface UserRepositoryPort {

    void putUser(User user, Password password);
}
