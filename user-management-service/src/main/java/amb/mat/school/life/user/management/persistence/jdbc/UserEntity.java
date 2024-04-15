package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

/**
 * No specific constraints on entities, business rules must be in the domain
 */
@Table("users")
public record UserEntity(
        @Id Long id,
        String username,
        String password,
        String emailAddress,
        @MappedCollection(idColumn = "user_id")
        Set<UserRoleEntity> roles,
        String owner
) {
}
