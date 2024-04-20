package amb.mat.school.life.studentservice.domain.student.command;

import amb.mat.school.life.studentservice.domain.student.Firstname;
import amb.mat.school.life.studentservice.domain.student.Lastname;
import amb.mat.school.life.studentservice.domain.user.Username;

import java.time.LocalDate;

public record CreateStudentCommand(
        Username username,
        Lastname lastname,
        Firstname firstname,
        LocalDate birthdate
) {

    public CreateStudentCommand {
        checkUsernameMandatory(username);
        checkLastnameMandatory(lastname);
        checkFirstnameMandatory(firstname);
        checkBirthdateMandatory(birthdate);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
    }

    private void checkLastnameMandatory(Lastname lastname) {
        if (lastname == null) {
            throw new IllegalArgumentException("lastname must not be null");
        }
    }

    private void checkFirstnameMandatory(Firstname firstname) {
        if (firstname == null) {
            throw new IllegalArgumentException("firstname must not be null");
        }
    }

    private void checkBirthdateMandatory(LocalDate birthdate) {
        if (birthdate == null) {
            throw new IllegalArgumentException("birthdate must not be null");
        }
    }
}
