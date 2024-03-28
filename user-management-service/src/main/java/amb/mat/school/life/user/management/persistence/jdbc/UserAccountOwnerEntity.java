package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.relational.core.mapping.Table;

@Table("user_account_owner")
public record UserAccountOwnerEntity(String username, String owner) {
}
