package amb.mat.school.life.user.management.controller;

import amb.mat.school.life.user.management.controller.dto.UserAccountDto;
import amb.mat.school.life.user.management.domain.UserAccount;

public class UserAccountDtoMapper {

    public UserAccountDto mapToDto(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.username().value(),
                userAccount.emailAddress().value(),
                userAccount.roles().stream().map(w -> w.name().toLowerCase()).toList()
        );
    }
}
