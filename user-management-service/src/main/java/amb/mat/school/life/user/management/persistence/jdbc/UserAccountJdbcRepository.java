package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface UserAccountJdbcRepository extends ListCrudRepository<UserAccountEntity, String> {

    @Query("""
            select count(*) > 0
            from user_account u
            join user_account_owner o on u.username = o.username
            where u.username = :username and o.owner = :owner
            """)
    boolean existsByUsernameAndOwner(String username, String owner);
}
