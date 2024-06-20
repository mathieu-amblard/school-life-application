package amb.mat.school.life.student.controller.dto;

import java.time.LocalDate;

public record CreateStudentCommandDto(
        String username,
        String password,
        String emailAddress,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
