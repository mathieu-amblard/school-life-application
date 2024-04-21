package amb.mat.school.life.user.management.persistence;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserJdbcRepository extends ListCrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsernameAndOwner(String username, String owner);
}
