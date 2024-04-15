package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.UserDto;
import amb.mat.school.life.user.management.domain.User;

public class UserDtoMapper {

    public UserDto mapToDto(User user) {
        return new UserDto(
                user.username().value(),
                user.emailAddress().value(),
                user.roles().stream().map(w -> w.name().toLowerCase()).toList()
        );
    }
}
