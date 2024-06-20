package amb.mat.school.life.student.controller.dto;

import java.time.LocalDate;

public record StudentDto(
        String studentId,
        String username,
        String emailAddress,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
