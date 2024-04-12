package amb.mat.school.life.user.management.controller.dto;

import java.util.Set;

public record PutUserAccountCommandDto(
        String password,
        String emailAddress,
        Set<String> roles
) {
}
