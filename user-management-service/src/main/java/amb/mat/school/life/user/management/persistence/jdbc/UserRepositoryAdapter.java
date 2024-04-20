package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.EncodedPassword;
import amb.mat.school.life.user.management.domain.User;
import amb.mat.school.life.user.management.domain.UserRepositoryPort;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJdbcRepository userJdbcRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryAdapter(UserJdbcRepository userJdbcRepository, UserEntityMapper userEntityMapper) {
        this.userJdbcRepository = userJdbcRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public boolean existsOwnerRelationshipBetween(Username username, Username owner) {
        return userJdbcRepository.existsByUsernameAndOwner(username.value(), owner.value());
    }

    @Override
    public Optional<User> findByUserName(Username username) {
        return userJdbcRepository.findByUsername(username.value())
                .map(userEntityMapper::mapToDomain);
    }

    @Override
    public void put(User user, EncodedPassword encodedPassword) {
        Long id = userJdbcRepository.findByUsername(user.username().value())
                .map(UserEntity::id)
                .orElse(null);
        UserEntity userEntity = userEntityMapper.mapToEntity(id, user, encodedPassword);
        userJdbcRepository.save(userEntity);
        userEntityMapper.mapToDomain(userEntity);
    }
}
