package amb.mat.school.life.teacherservice.application.command;

import amb.mat.school.life.teacherservice.domain.teacher.Firstname;
import amb.mat.school.life.teacherservice.domain.teacher.Lastname;
import amb.mat.school.life.teacherservice.domain.teacher.Resume;
import amb.mat.school.life.teacherservice.domain.teacher.command.Command;
import amb.mat.school.life.teacherservice.domain.user.EmailAddress;
import amb.mat.school.life.teacherservice.domain.user.Password;
import amb.mat.school.life.teacherservice.domain.user.Username;

public record CreateTeacherAndUserCommand(
        Username username,
        Password password,
        EmailAddress emailAddress,
        Lastname lastname,
        Firstname firstname,
        Resume resume
) implements Command {

    private static final String ERROR_MESSAGE_TEMPLATE = "to create a new teacher, %s";

    public CreateTeacherAndUserCommand {
        checkUsernameMandatory(username);
        checkPasswordMandatory(password);
        checkEmailAddressMandatory(emailAddress);
        checkLastname(lastname);
        checkFirstname(firstname);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
        }
    }

    private void checkPasswordMandatory(Password password) {
        if (password == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the password is required"));
        }
    }

    private void checkEmailAddressMandatory(EmailAddress emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the email address is required"));
        }
    }

    private void checkLastname(Lastname username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the lastname is required"));
        }
    }

    private void checkFirstname(Firstname username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the firstname is required"));
        }
    }
}
