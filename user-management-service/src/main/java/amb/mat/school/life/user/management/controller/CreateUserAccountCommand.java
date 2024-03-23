package amb.mat.school.life.user.management.controller;

import java.util.List;

public record CreateUserAccountCommand(String username, String password, List<String> roles) {
}
