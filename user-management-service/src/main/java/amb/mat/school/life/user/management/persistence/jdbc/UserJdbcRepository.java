package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.repository.ListCrudRepository;

public interface UserJdbcRepository extends ListCrudRepository<UserEntity, String> {

    boolean existsByUsernameAndOwner(String username, String owner);
}
