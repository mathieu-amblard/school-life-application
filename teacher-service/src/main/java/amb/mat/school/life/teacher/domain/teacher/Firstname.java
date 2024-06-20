package amb.mat.school.life.teacher.domain.teacher;

/**
 * Business Rules :
 * - The firstname is mandatory.
 * - The firstname is starts with uppercase.
 * - The firstname contains only letters and spaces, dashes and quotes.
 */
public record Firstname(String value) {

    private static final String FIRSTNAME_REGEX = "[A-Z][A-Za-z '-]*";

    public Firstname {
        checkMandatory(value);
        checkValid(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkValid(String value) {
        if (value != null && !value.matches(FIRSTNAME_REGEX)) {
            throw new IllegalArgumentException("value %s must be a valid firstname".formatted(value));
        }
    }
}
