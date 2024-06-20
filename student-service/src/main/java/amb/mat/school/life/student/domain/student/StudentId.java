package amb.mat.school.life.student.domain.student;

import amb.mat.school.life.student.domain.Identifier;

import java.util.UUID;

public record StudentId(String value) implements Identifier {

    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public StudentId {
        checkStudentIdMandatory(value);
        checkStudentIdFormat(value);
    }

    public StudentId(UUID uuid) {
        this(uuid.toString());
    }

    private void checkStudentIdMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkStudentIdFormat(String value) {
        if (value != null && !value.matches(UUID_REGEX)) {
            throw new IllegalArgumentException("value must be a valid uuid");
        }
    }
}
