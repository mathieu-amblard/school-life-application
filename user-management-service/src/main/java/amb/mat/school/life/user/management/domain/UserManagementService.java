package amb.mat.school.life.user.management.domain;

public interface UserManagementService {

    boolean isOwner(Username owner, Username username);
}
