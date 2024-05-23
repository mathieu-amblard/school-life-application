package amb.mat.school.life.teacherservice.controller.dto;

import java.time.LocalDate;

public record TeacherDto(
        String teacherId,
        String username,
        String emailAddress,
        String lastname,
        String firstname,
        LocalDate birthdate
) {
}
