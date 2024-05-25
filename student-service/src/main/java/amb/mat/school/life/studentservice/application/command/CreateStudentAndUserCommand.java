package amb.mat.school.life.studentservice.application.command;

import amb.mat.school.life.studentservice.domain.student.Birthdate;
import amb.mat.school.life.studentservice.domain.student.Firstname;
import amb.mat.school.life.studentservice.domain.student.Lastname;
import amb.mat.school.life.studentservice.domain.user.EmailAddress;
import amb.mat.school.life.studentservice.domain.user.Password;
import amb.mat.school.life.studentservice.domain.user.Username;

public record CreateStudentAndUserCommand(
        Username username,
        Password password,
        EmailAddress emailAddress,
        Lastname lastname,
        Firstname firstname,
        Birthdate birthdate
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to create a new student, %s";

    public CreateStudentAndUserCommand {
        checkUsernameMandatory(username);
        checkPasswordMandatory(password);
        checkEmailAddressMandatory(emailAddress);
        checkLastname(lastname);
        checkFirstname(firstname);
        checkBirthdate(birthdate);
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

    private void checkLastname(Lastname lastname) {
        if (lastname == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the lastname is required"));
        }
    }

    private void checkFirstname(Firstname firstname) {
        if (firstname == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the firstname is required"));
        }
    }

    private void checkBirthdate(Birthdate birthdate) {
        if (birthdate == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the birthdate is required"));
        }
    }
}
