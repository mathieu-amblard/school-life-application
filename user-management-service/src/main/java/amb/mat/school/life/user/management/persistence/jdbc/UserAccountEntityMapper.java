package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Role;
import amb.mat.school.life.user.management.domain.UserAccount;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Optional;
import java.util.stream.Collectors;

public class UserAccountEntityMapper {

    public UserAccount mapToDomain(UserAccountEntity userAccountEntity) {
        return new UserAccount(
                new Username(userAccountEntity.username()),
                new EmailAddress(userAccountEntity.emailAddress()),
                userAccountEntity.roles().stream().map(UserAccountRoleEntity::role).map(Role::of).collect(Collectors.toSet()),
                Optional.ofNullable(userAccountEntity.owner()).map(UserAccountOwnerEntity::owner).map(Username::new).orElse(null)
        );
    }

    public UserAccountEntity mapToEntity(UserAccount userAccount) {
        Username username = userAccount.username();
        return new UserAccountEntity(
                username.value(),
                userAccount.emailAddress().value(),
                userAccount.roles().stream().map(role -> new UserAccountRoleEntity(username.value(), role.name())).collect(Collectors.toSet()),
                new UserAccountOwnerEntity(username.value(), userAccount.owner().value())
        );
    }
}
