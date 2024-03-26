package amb.mat.school.life.user.management.domain;

/**
 * Business Rules :
 * - The username is mandatory.
 * - The username consists of 6 to 30 characters inclusive.
 * - The first character of the username must be an alphabetic character.
 * - The username can only contain alphanumeric characters and underscores (_).
 */
public record Username(String value) {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 30;
    private static final String VALID_CHARACTERS_REGEX = "\\w*";

    public Username {
        checkMandatory();
        checkLength();
        checkFirstCharacter();
        checkContainsAlphanumericAndUnderscores();
    }

    private void checkMandatory() {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkLength() {
        if (value != null && (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH)) {
            throw new IllegalArgumentException("value must must contain between %s to %s characters".formatted(MIN_LENGTH, MAX_LENGTH));
        }
    }

    private void checkFirstCharacter() {
        if (value != null && !Character.isLetter(value.charAt(0))) {
            throw new IllegalArgumentException("value must start with a letter");
        }
    }

    private void checkContainsAlphanumericAndUnderscores() {
        if (value != null && !value.matches(VALID_CHARACTERS_REGEX)) {
            throw new IllegalArgumentException("value must contain alphanumeric characters and underscores only");
        }
    }
}
