package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public record UserRoleEntity(String username, String role) {
}
