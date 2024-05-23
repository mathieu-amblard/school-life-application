package amb.mat.school.life.teacherservice.controller.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record PatchTeacherCommandDto(
        @Nullable String lastname,
        @Nullable String firstname,
        @Nullable LocalDate birthdate
) {
}
