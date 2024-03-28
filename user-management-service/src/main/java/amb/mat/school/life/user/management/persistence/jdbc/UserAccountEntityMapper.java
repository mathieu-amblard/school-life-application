package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Role;
import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;

public class UserAccountEntityMapper {

    public UserAccount mapToDomain(UserAccountEntity userAccountEntity) {
        return new UserAccount(
                new Username(userAccountEntity.username()),
                new EmailAddress(userAccountEntity.emailAddress()),
                userAccountEntity.roles().stream().map(UserAccountRoleEntity::role).map(Role::of).toList(),
                Optional.ofNullable(userAccountEntity.owner()).map(UserAccountOwnerEntity::owner).map(Username::new).orElse(null)
        );
    }
}
