package amb.mat.school.life.studentservice.controller.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UpdatePartiallyStudentCommandDto(
        @Nullable String lastname,
        @Nullable String firstname,
        @Nullable LocalDate birthdate
) {
}
