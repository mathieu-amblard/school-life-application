package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserAccountJdbcRepository extends ListCrudRepository<UserAccountEntity, Long> {

    Optional<UserAccountEntity> findByUsername(String username);

    boolean existsByUsernameAndOwner(String username, String owner);
}
