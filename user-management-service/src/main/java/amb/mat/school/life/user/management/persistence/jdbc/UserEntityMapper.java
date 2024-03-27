package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Role;
import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;

public class UserEntityMapper {

    public UserAccount mapToDomain(UserEntity userEntity) {
        return new UserAccount(
                new Username(userEntity.username()),
                new EmailAddress(userEntity.emailAddress()),
                userEntity.authorities().stream().map(Role::valueOf).toList(),
                Optional.ofNullable(userEntity.owner()).map(OwnerEntity::owner).map(Username::new).orElse(null)
        );
    }
}
