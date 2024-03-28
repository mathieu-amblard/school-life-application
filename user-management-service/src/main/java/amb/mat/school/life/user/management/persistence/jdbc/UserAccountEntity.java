package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

/**
 * No constraint on entities, business rules must be in the domain
 */
@Table("user_account")
public record UserAccountEntity(
        @Id String username,
        String emailAddress,
        @MappedCollection(idColumn = "username", keyColumn = "role")
        Set<UserAccountRoleEntity> roles,
        @MappedCollection(idColumn = "username")
        UserAccountOwnerEntity owner
) {
}
