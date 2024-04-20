package amb.mat.school.life.studentservice.domain.student;

// TODO record ?

import amb.mat.school.life.studentservice.domain.student.command.CreateStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import amb.mat.school.life.studentservice.domain.user.Username;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

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
    private final Username username; // referenced the user by its username to have a loosely coupling
    private Lastname lastname;
    private Firstname firstname;
    private LocalDate birthdate;

    public Student(StudentId studentId, Username username, Lastname lastname, Firstname firstname, LocalDate birthdate) {
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

    /**
     * Create a new student from a command
     *
     * @param command the {@link CreateStudentCommand} used to create a student
     */
    public Student(CreateStudentCommand command) {
        this(
                new StudentId(UUID.randomUUID()),
                command.username(),
                command.lastname(),
                command.firstname(),
                command.birthdate()
        );
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

    /**
     * Update the information of the current student
     *
     * @param command the {@link UpdateStudentCommand} used to update the student
     * @return {@code true} if the current student has been update, false otherwise
     */
    public boolean updateInformation(UpdateStudentCommand command) {
        boolean needsUpdate =
                (command.lastname() != null && !command.lastname().equals(lastname)) ||
                        (command.firstname() != null && !command.firstname().equals(firstname)) ||
                        (command.birthdate() != null && !command.birthdate().equals(birthdate));
        lastname = Optional.ofNullable(command.lastname()).orElse(lastname);
        firstname = Optional.ofNullable(command.firstname()).orElse(firstname);
        birthdate = Optional.ofNullable(command.birthdate()).orElse(birthdate);
        return needsUpdate;
    }
}
