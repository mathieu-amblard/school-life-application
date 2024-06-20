package amb.mat.school.life.teacher.domain.teacher;

import amb.mat.school.life.teacher.domain.Identifier;

import java.util.UUID;

public record TeacherId(String value) implements Identifier {

    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public TeacherId {
        checkTeacherIdMandatory(value);
        checkTeacherIdFormat(value);
    }

    public TeacherId(UUID uuid) {
        this(uuid.toString());
    }

    private void checkTeacherIdMandatory(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
    }

    private void checkTeacherIdFormat(String value) {
        if (value != null && !value.matches(UUID_REGEX)) {
            throw new IllegalArgumentException("value must be a valid uuid");
        }
    }
}
