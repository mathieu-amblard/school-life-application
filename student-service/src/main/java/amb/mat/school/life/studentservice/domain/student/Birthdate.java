package amb.mat.school.life.studentservice.domain.student;

import java.time.LocalDate;

/**
 * Business Rules :
 * - The birthdate is mandatory.
 * - The birthdate is not in the future.
 * - The birthdate is greater than the current date minus 18 years.
 */
public record Birthdate(LocalDate value) {

    public static final int MAX_AGE = 18;

    public Birthdate {
        checkMandatory(value);
        checkNotInFuture(value);
        checkIsUnder18(value);
    }

    private void checkMandatory(LocalDate value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkNotInFuture(LocalDate value) {
        if (value != null && value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("value %s must not be in the future".formatted(value));
        }
    }

    private void checkIsUnder18(LocalDate value) {
        if (value != null && value.isBefore(LocalDate.now().minusYears(MAX_AGE))) {
            throw new IllegalArgumentException("value %s must be a valid email address".formatted(value));
        }
    }
}
