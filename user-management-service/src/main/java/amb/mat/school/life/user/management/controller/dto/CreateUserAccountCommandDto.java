package amb.mat.school.life.user.management.controller.dto;

import java.util.Set;

public record CreateUserAccountCommandDto(
        String username,
        String emailAddress,
        String password,
        Set<String> roles
) {
}
