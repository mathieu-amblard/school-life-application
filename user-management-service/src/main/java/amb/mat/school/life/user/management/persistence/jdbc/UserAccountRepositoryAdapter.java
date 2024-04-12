package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.EncodedPassword;
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
    public boolean existsOwnerRelationshipBetween(Username username, Username owner) {
        return userAccountJdbcRepository.existsByUsernameAndOwner(username.value(), owner.value());
    }

    @Override
    public Optional<UserAccount> findByUserName(Username username) {
        return userAccountJdbcRepository.findByUsername(username.value())
                .map(userAccountEntityMapper::mapToDomain);
    }

    @Override
    public UserAccount put(UserAccount userAccount) {
        return this.put(userAccount, null);
    }

    @Override
    public UserAccount put(UserAccount userAccount, EncodedPassword encodedPassword) {
        Long id = userAccountJdbcRepository.findByUsername(userAccount.username().value())
                .map(UserAccountEntity::id)
                .orElse(null);
        UserAccountEntity userAccountEntity = userAccountEntityMapper.mapToEntity(id, userAccount, encodedPassword);
        userAccountJdbcRepository.save(userAccountEntity);
        return userAccountEntityMapper.mapToDomain(userAccountEntity);
    }
}
