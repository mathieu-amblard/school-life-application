package amb.mat.school.life.user.management.persistence.jdbc;

import org.springframework.data.annotation.Id;

import java.util.List;

public record UserAccountEntity(
        @Id String username,
        String emailAddress,
        List<String> authorities,
        String owner
) {
}
