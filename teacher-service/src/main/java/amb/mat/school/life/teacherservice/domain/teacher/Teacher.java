package amb.mat.school.life.teacherservice.domain.teacher;

import amb.mat.school.life.teacherservice.domain.teacher.command.CreateTeacherCommand;
import amb.mat.school.life.teacherservice.domain.teacher.command.UpdateTeacherCommand;
import amb.mat.school.life.teacherservice.domain.user.Username;

import java.util.UUID;

/**
 * Business Rules :
 * - The teacher identifier is mandatory.
 * - The username is mandatory. (reference between aggregates → no foreign keys but reference by simple IDs to have a relatively loose coupling)
 * - The lastname is mandatory.
 * - The firstname is mandatory.
 * - The resume is optional, it can be set later in the process.
 */
public class Teacher {

    private final TeacherId teacherId;
    private final Username username; // referenced the user by its username to have a loosely coupling
    private Lastname lastname;
    private Firstname firstname;
    private Resume resume;

    public Teacher(TeacherId teacherId, Username username, Lastname lastname, Firstname firstname, Resume resume) {
        checkTeacherIdMandatory(teacherId);
        checkUsernameMandatory(username);
        checkLastnameMandatory(lastname);
        checkFirstnameMandatory(firstname);
        this.teacherId = teacherId;
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.resume = resume;
    }

    /**
     * Create a new teacher from a command
     *
     * @param command the {@link CreateTeacherCommand} used to create a teacher
     */
    public Teacher(CreateTeacherCommand command) {
        this(
                new TeacherId(UUID.randomUUID()),
                command.username(),
                command.lastname(),
                command.firstname(),
                command.resume()
        );
    }

    private void checkTeacherIdMandatory(TeacherId teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("teacher identifier must not be null");
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

    public TeacherId teacherId() {
        return teacherId;
    }

    public Username username() {
        return username;
    }

    public Lastname lastname() {
        return lastname;
    }

    public Firstname firstname() {
        return firstname;
    }

    public Resume resume() {
        return resume;
    }

    /**
     * Update the information of the current teacher
     *
     * @param command the {@link UpdateTeacherCommand} used to update the teacher
     * @return {@code true} if the current teacher has been update, false otherwise
     */
    public boolean updateInformation(UpdateTeacherCommand command) {
        boolean updatedLastname = updateLastname(command.lastname());
        boolean updatedFirstname = updateFirstname(command.firstname());
        boolean updatedResume = updateResume(command.resume());
        return updatedLastname || updatedFirstname || updatedResume;
    }

    private boolean updateLastname(Lastname newValue) {
        if (newValue != null && !newValue.equals(lastname)) {
            lastname = newValue;
            return true;
        }
        return false;
    }

    private boolean updateFirstname(Firstname newValue) {
        if (newValue != null && !newValue.equals(firstname)) {
            firstname = newValue;
            return true;
        }
        return false;
    }

    private boolean updateResume(Resume newValue) {
        if (newValue != null && !newValue.equals(resume)) {
            resume = newValue;
            return true;
        }
        return false;
    }
}
