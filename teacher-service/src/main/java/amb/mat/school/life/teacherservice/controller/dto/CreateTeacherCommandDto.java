package amb.mat.school.life.teacherservice.controller.dto;

import java.time.LocalDate;

public record CreateTeacherCommandDto(
        String username,
        String password,
        String emailAddress,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
