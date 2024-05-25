package amb.mat.school.life.teacherservice.controller.dto;

import amb.mat.school.life.teacherservice.application.command.CreateTeacherAndUserCommand;
import amb.mat.school.life.teacherservice.domain.teacher.Firstname;
import amb.mat.school.life.teacherservice.domain.teacher.Lastname;
import amb.mat.school.life.teacherservice.domain.teacher.Resume;
import amb.mat.school.life.teacherservice.domain.teacher.command.CommandSupplier;
import amb.mat.school.life.teacherservice.domain.user.EmailAddress;
import amb.mat.school.life.teacherservice.domain.user.Password;
import amb.mat.school.life.teacherservice.domain.user.Username;

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
