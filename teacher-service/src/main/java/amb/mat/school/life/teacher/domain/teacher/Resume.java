package amb.mat.school.life.teacher.domain.teacher;

/**
 * Business Rules :
 * - The resume can be null.
 * - The resume must not exceed 500 chars.
 */
public record Resume(String value) {

    public static final int MAX_LENGTH = 1500;

    public Resume {
        checkNotExceedMaxLength(value);
    }

    private void checkNotExceedMaxLength(String value) {
        if (value != null && value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("value length %d must not exceed maximum length %d".formatted(value.length(), MAX_LENGTH));
        }
    }

    boolean exists() {
        return value != null;
    }
}
