package amb.mat.school.life.teacherservice.controller.dto;

import amb.mat.school.life.teacherservice.domain.teacher.command.CommandSupplier;
import amb.mat.school.life.teacherservice.domain.teacher.command.UpdateTeacherCommand;
import jakarta.annotation.Nullable;

public record PatchTeacherCommandDto(
        @Nullable String lastname,
        @Nullable String firstname,
        @Nullable String resume
) implements CommandSupplier<String, UpdateTeacherCommand> {

    @Override
    public UpdateTeacherCommand getCommand(String identifier) {
        return new UpdateTeacherCommand(
                identifier,
                lastname(),
                firstname(),
                resume()
        );
    }
}
