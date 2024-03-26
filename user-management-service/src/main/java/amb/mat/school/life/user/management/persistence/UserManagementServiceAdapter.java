package amb.mat.school.life.user.management.persistence;

import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.domain.Username;
import org.springframework.transaction.annotation.Transactional;

public class UserManagementServiceAdapter {

    private final UserAccountRepository userAccountRepository;

    public UserManagementServiceAdapter(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Username owner, Username username) {
        return userAccountRepository.isOwner(owner, username);
    }
}
