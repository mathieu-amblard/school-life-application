package amb.mat.school.life.studentservice.domain.student;

/**
 * Business Rules :
 * - The lastname is mandatory.
 * - The lastname is starts with uppercase.
 * - The lastname contains only letters and spaces, dashes and quotes.
 */
public record Lastname(String value) {

    private static final String LASTNAME_REGEX = "[A-Z]+([ '-][a-zA-Z]+)*";

    public Lastname {
        checkMandatory(value);
        checkValid(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkValid(String value) {
        if (value != null && !value.matches(LASTNAME_REGEX)) {
            throw new IllegalArgumentException("value %s must be a valid lastname".formatted(value));
        }
    }
}
