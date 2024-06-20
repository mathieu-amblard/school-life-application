package amb.mat.school.life.teacher.controller.dto;

import amb.mat.school.life.teacher.application.command.CreateTeacherAndUserCommand;
import amb.mat.school.life.teacher.domain.teacher.Lastname;
import amb.mat.school.life.teacher.domain.user.Password;
import amb.mat.school.life.teacher.domain.user.Username;
import amb.mat.school.life.teacher.domain.teacher.Firstname;
import amb.mat.school.life.teacher.domain.teacher.Resume;
import amb.mat.school.life.teacher.domain.teacher.command.CommandSupplier;
import amb.mat.school.life.teacher.domain.user.EmailAddress;

public record CreateTeacherCommandDto(
        String username,
        String password,
        String emailAddress,
        String lastname,
        String firstname,
        String resume
) implements CommandSupplier<Void, CreateTeacherAndUserCommand> {

    @Override
    public CreateTeacherAndUserCommand getCommand(Void ignored) {
        return new CreateTeacherAndUserCommand(
                new Username(username()),
                new Password(password()),
                new EmailAddress(emailAddress()),
                new Lastname(lastname()),
                new Firstname(firstname()),
                new Resume(resume())
        );
    }
}
