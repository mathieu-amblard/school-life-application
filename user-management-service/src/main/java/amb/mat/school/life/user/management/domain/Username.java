package amb.mat.school.life.user.management.domain;

/**
 * Business Rules :
 * - The username is mandatory.
 * - The username consists of 5 to 30 characters inclusive.
 * - The first character of the username must be an alphabetic character.
 * - The username can only contain alphanumeric characters and underscores (_).
 */
public record Username(String value) {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 30;
    private static final String VALID_CHARACTERS_REGEX = "\\w*";

    public Username {
        checkMandatory(value);
        checkLength(value);
        checkFirstCharacter(value);
        checkContainsAlphanumericAndUnderscores(value);
    }

    private void checkMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkLength(String value) {
        if (value != null && (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH)) {
            throw new IllegalArgumentException("value must must contain between %s to %s characters".formatted(MIN_LENGTH, MAX_LENGTH));
        }
    }

    private void checkFirstCharacter(String value) {
        if (value != null && !Character.isLetter(value.charAt(0))) {
            throw new IllegalArgumentException("value must start with a letter");
        }
    }

    private void checkContainsAlphanumericAndUnderscores(String value) {
        if (value != null && !value.matches(VALID_CHARACTERS_REGEX)) {
            throw new IllegalArgumentException("value must contain alphanumeric characters and underscores only");
        }
    }
}
