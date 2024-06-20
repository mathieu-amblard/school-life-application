package amb.mat.school.life.student.controller.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record PatchStudentCommandDto(
        @Nullable String lastname,
        @Nullable String firstname,
        @Nullable LocalDate birthdate
) {
}
