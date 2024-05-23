package amb.mat.school.life.teacherservice.domain.user;

/**
 * Business Rules :
 * - The password is mandatory.
 * <p>
 * NB : It is not the responsibility of the teacher-service to validate the username format.
 */
public record Password(String value) {

    public Password {
        checkMandatory(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }
}