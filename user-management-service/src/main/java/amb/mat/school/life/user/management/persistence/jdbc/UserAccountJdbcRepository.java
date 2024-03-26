package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.repository.ListCrudRepository;

public interface UserAccountJdbcRepository extends ListCrudRepository<UserAccountEntity, String> {
}
