package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;

public class UserAccountRepositoryAdapter implements UserAccountRepository {

    private final UserAccountJdbcRepository userAccountJdbcRepository;
    private final UserAccountEntityMapper userAccountEntityMapper;

    public UserAccountRepositoryAdapter(UserAccountJdbcRepository userAccountJdbcRepository, UserAccountEntityMapper userAccountEntityMapper) {
        this.userAccountJdbcRepository = userAccountJdbcRepository;
        this.userAccountEntityMapper = userAccountEntityMapper;
    }

    @Override
    public boolean existsOwnerRelationship(Username username, Username owner) {
        return userAccountJdbcRepository.existsByUsernameAndOwner(username.value(), owner.value());
    }

    @Override
    public Optional<UserAccount> findByUserName(Username username) {
        return userAccountJdbcRepository.findById(username.value())
                .map(userAccountEntityMapper::mapToDomain);
    }
}
