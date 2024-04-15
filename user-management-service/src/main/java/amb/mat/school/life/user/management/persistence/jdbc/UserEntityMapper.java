package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class UserEntityMapper {

    public User mapToDomain(UserEntity userEntity) {
        return new User(
                new Username(userEntity.username()),
                new EmailAddress(userEntity.emailAddress()),
                userEntity.roles().stream().map(UserRoleEntity::role).map(Role::of).collect(Collectors.toSet()),
                Optional.ofNullable(userEntity.owner()).map(Username::new).orElse(null)
        );
    }

    public UserEntity mapToEntity(Long id, User user, EncodedPassword encodedPassword) {
        Username username = user.username();
        return new UserEntity(
                id,
                username.value(),
                Optional.ofNullable(encodedPassword).map(EncodedPassword::value).orElse(null),
                user.emailAddress().value(),
                user.roles().stream().map(role -> new UserRoleEntity(username.value(), role.name().toLowerCase())).collect(Collectors.toSet()),
                user.owner().value()
        );
    }
}
