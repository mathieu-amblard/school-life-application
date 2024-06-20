package amb.mat.school.life.teacher.domain.teacher.command;

import amb.mat.school.life.teacher.domain.teacher.Lastname;
import amb.mat.school.life.teacher.domain.user.Username;
import amb.mat.school.life.teacher.domain.teacher.Firstname;
import amb.mat.school.life.teacher.domain.teacher.Resume;

public record CreateTeacherCommand(
        Username username,
        Lastname lastname,
        Firstname firstname,
        Resume resume
) implements Command {

    private static final String ERROR_MESSAGE_TEMPLATE = "to create a new teacher, %s";

    public CreateTeacherCommand {
        checkUsernameMandatory(username);
        checkLastname(lastname);
        checkFirstname(firstname);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
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
