package amb.mat.school.life.studentservice.domain.student;

import java.util.UUID;

public record StudentId(String value) {

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
        try {
            if (value != null) {
                UUID.fromString(value);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("value must be a uuid");
        }
    }
}
