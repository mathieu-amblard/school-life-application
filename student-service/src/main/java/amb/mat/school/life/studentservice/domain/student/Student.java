package amb.mat.school.life.studentservice.domain.student;

// TODO record ?

import amb.mat.school.life.studentservice.domain.user.Username;

import java.time.LocalDate;

/**
 * Business Rules :
 * - The student identifier is mandatory.
 * - The username is mandatory. (reference between aggregates → no foreign keys but reference by simple IDs to have a relatively loose coupling)
 * - The lastname is mandatory.
 * - The firstname is mandatory.
 * - The birthdate is mandatory.
 */
public class Student {

    private final StudentId studentId;
    private final Username username;
    private final String lastname;
    private final String firstname;
    private final LocalDate birthdate;

    public Student(StudentId studentId, Username username, String lastname, String firstname, LocalDate birthdate) {
        checkStudentIdMandatory(studentId);
        checkUsernameMandatory(username);
        checkLastnameMandatory(lastname);
        checkFirstnameMandatory(firstname);
        checkBirthdateMandatory(birthdate);
        this.studentId = studentId;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthdate = birthdate;
    }

    private void checkStudentIdMandatory(StudentId studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("student identifier must not be null");
        }
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
    }

    private void checkLastnameMandatory(String lastname) {
        if (lastname == null) {
            throw new IllegalArgumentException("lastname must not be null");
        }
    }

    private void checkFirstnameMandatory(String firstname) {
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
