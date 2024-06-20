package amb.mat.school.life.student.domain.student.command;

import amb.mat.school.life.student.domain.student.Birthdate;
import amb.mat.school.life.student.domain.student.Firstname;
import amb.mat.school.life.student.domain.student.Lastname;
import amb.mat.school.life.student.domain.user.Username;

public record CreateStudentCommand(
        Username username,
        Lastname lastname,
        Firstname firstname,
        Birthdate birthdate
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to create a new student, %s";

    public CreateStudentCommand {
        checkUsernameMandatory(username);
        checkLastname(lastname);
        checkFirstname(firstname);
        checkBirthdate(birthdate);
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

    private void checkBirthdate(Birthdate username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the birthdate is required"));
        }
    }
}
