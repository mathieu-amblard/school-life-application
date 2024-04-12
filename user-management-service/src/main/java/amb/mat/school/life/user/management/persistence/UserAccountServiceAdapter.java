package amb.mat.school.life.user.management.persistence;

import amb.mat.school.life.user.management.domain.*;
import amb.mat.school.life.user.management.domain.command.CreateUserAccountCommand;
import amb.mat.school.life.user.management.domain.query.FindUserAccountQuery;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UserAccountServiceAdapter implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceAdapter(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
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
        return userAccountRepository.existsOwnerRelationshipBetween(query.username(), query.owner());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAccount> find(FindUserAccountQuery query) {
        return userAccountRepository.findByUserName(query.username());
    }

    @Override
    @Transactional
    public UserAccount createAccount(CreateUserAccountCommand command) {
        UserAccount userAccount = new UserAccount(
                command.username(),
                command.emailAddress(),
                command.roles(),
                command.owner()
        );
        EncodedPassword encodedPassword = passwordEncoder.encode(command.password());
        return userAccountRepository.put(userAccount, encodedPassword);
    }
}
