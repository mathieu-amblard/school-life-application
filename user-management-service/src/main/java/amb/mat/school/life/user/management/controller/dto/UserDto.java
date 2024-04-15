package amb.mat.school.life.user.management.controller.dto;

import java.util.List;

public record UserDto(
        String username,
        String email,
        List<String> roles
) {
}
