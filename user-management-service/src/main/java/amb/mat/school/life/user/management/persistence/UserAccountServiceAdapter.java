package amb.mat.school.life.user.management.persistence;

import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.domain.UserAccountService;
import amb.mat.school.life.user.management.domain.Username;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

public class UserAccountServiceAdapter implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceAdapter(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Username getMyUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new Username(username);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccount getMyAccount() {
        return userAccountRepository.findByUserName(getMyUsername())
                .orElseThrow(() -> new IllegalStateException("the authenticated user must have an user account"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOwnedBy(IsOwnedByQuery query) {
        return userAccountRepository.existsOwnerRelationship(query.username(), query.owner());
    }
}
