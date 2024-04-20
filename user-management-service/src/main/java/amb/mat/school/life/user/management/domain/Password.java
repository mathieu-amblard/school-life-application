package amb.mat.school.life.user.management.domain;

import java.util.regex.Pattern;

/**
 * Business Rules :
 * - The password is mandatory.
 * - The password consists of 8 to 20 characters inclusive.
 * - The password includes a capital letter.
 * - The password includes at least one lowercase letter.
 * - The password includes at least one digit.
 * - The password includes at least one special character.
 * - The password does not contain tabs or spaces.
 */
public record Password(String value) {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    private static final String ONE_DIGIT = "(?=.*[0-9])";
    private static final String LOWER_CASE = "(?=.*[a-z])";
    private static final String UPPER_CASE = "(?=.*[A-Z])";
    private static final String SPECIAL_CHAR = "(?=.*[@#$%^&+=*])";
    private static final String NO_SPACE = "(?=\\S+$)";
    private static final String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR
    );

    public Password {
        checkMandatory(value);
        checkPattern(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkPattern(String value) {
        if (value != null && !PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("""
                    value must : \
                    - contain between %s to %s characters \
                    - include a capital letter \
                    - include at least one lowercase letter \
                    - include at least one digit \
                    - include at least one special character \
                    - not contain tabs or spaces \
                    """.formatted(MIN_LENGTH, MAX_LENGTH)
            );
        }
    }
}
