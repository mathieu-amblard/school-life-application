package amb.mat.school.life.user.management.controller.dto;

import java.util.List;

public record CreateUserAccountCommandDto(String username, String password, List<String> roles) {
}
