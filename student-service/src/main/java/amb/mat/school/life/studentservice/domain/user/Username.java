package amb.mat.school.life.studentservice.domain.user;

/**
 * Business Rules :
 * - The username is mandatory.
 * <p>
 * NB : It is not the responsibility of the student-service to validate the username format.
 */
public record Username(String value) {

    public Username {
        checkMandatory(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }
}
