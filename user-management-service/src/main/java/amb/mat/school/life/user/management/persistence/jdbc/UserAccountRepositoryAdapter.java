package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;

public class UserAccountRepositoryAdapter implements UserAccountRepository {

    private final UserJdbcRepository userJdbcRepository;
    private final UserEntityMapper userEntityMapper;

    public UserAccountRepositoryAdapter(UserJdbcRepository userJdbcRepository, UserEntityMapper userEntityMapper) {
        this.userJdbcRepository = userJdbcRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public boolean existsOwnerRelationship(Username username, Username owner) {
        return userJdbcRepository.existsByUsernameAndOwner(username.value(), owner.value());
    }

    @Override
    public Optional<UserAccount> findByUserName(Username username) {
        return userJdbcRepository.findById(username.value())
                .map(userEntityMapper::mapToDomain);
    }
}
