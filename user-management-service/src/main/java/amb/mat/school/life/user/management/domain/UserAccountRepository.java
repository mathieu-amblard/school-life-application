package amb.mat.school.life.user.management.domain;

public interface UserAccountRepository {

    boolean isOwner(Username owner, Username username);
}
