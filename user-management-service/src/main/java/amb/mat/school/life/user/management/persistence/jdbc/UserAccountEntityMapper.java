package amb.mat.school.life.user.management.persistence.jdbc;

import amb.mat.school.life.user.management.domain.*;

import java.util.Optional;
import java.util.stream.Collectors;

public class UserAccountEntityMapper {

    public UserAccount mapToDomain(UserAccountEntity userAccountEntity) {
        return new UserAccount(
                new Username(userAccountEntity.username()),
                new EmailAddress(userAccountEntity.emailAddress()),
                userAccountEntity.roles().stream().map(UserAccountRoleEntity::role).map(Role::of).collect(Collectors.toSet()),
                Optional.ofNullable(userAccountEntity.owner()).map(Username::new).orElse(null)
        );
    }

    public UserAccountEntity mapToEntity(Long id, UserAccount userAccount, EncodedPassword encodedPassword) {
        Username username = userAccount.username();
        return new UserAccountEntity(
                id,
                username.value(),
                Optional.ofNullable(encodedPassword).map(EncodedPassword::value).orElse(null),
                userAccount.emailAddress().value(),
                userAccount.roles().stream().map(role -> new UserAccountRoleEntity(username.value(), role.name().toLowerCase())).collect(Collectors.toSet()),
                userAccount.owner().value()
        );
    }
}
