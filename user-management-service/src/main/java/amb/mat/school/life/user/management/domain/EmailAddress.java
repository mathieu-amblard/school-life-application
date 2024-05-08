package amb.mat.school.life.user.management.domain;

/**
 * Business Rules :
 * - The emailAddress address is mandatory.
 * - The emailAddress address is valid.
 */
public record EmailAddress(String value) {

    private static final String EMAIL_FIRST_PART_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*";
    private static final String EMAIL_SECOND_PART_REGEX = "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_REGEX = "%s@%s".formatted(EMAIL_FIRST_PART_REGEX, EMAIL_SECOND_PART_REGEX);

    public EmailAddress {
        checkMandatory(value);
        checkValid(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkValid(String value) {
        if (value != null && !value.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("value %s must be a valid emailAddress address".formatted(value));
        }
    }
}
