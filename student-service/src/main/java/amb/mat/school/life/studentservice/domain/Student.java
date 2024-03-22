package amb.mat.school.life.studentservice.domain;

// TODO record ?

/**
 * Business Rules :
 * - The username is mandatory.
 * - The lastname is mandatory.
 * - The firstname is mandatory.
 * - The date of birth is also mandatory.
 */
public class Student {

    private final StudentId studentId;
    private final String lastname;
    private final String firstname;
    private final String dateOfBirth;

    public Student(StudentId studentId, String lastname, String firstname, String dateOfBirth) {
        this.studentId = studentId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.dateOfBirth = dateOfBirth;
    }
}
