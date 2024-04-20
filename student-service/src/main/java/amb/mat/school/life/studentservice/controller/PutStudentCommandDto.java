package amb.mat.school.life.studentservice.controller;

import java.time.LocalDate;

public record PutStudentCommandDto(
        String username,
        String password,
        String emailAddress,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
