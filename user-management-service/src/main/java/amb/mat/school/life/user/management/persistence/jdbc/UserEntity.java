package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/**
 * No constraint on entities, business rules must be in the domain
 */
@Table("users")
public record UserEntity(
        @Id String username,
        String emailAddress,
        List<String> authorities,
        OwnerEntity owner
) {

//    public UserEntity(
//            String username,
//            String emailAddress,
//            List<String> authorities,
//            OwnerEntity owner
//    ) {
//        this(username, emailAddress, authorities, owner.owner());
//    }
}
