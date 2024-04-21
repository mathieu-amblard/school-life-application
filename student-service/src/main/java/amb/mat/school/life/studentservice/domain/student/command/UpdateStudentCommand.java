package amb.mat.school.life.studentservice.domain.student.command;

import amb.mat.school.life.studentservice.domain.student.Birthdate;
import amb.mat.school.life.studentservice.domain.student.Firstname;
import amb.mat.school.life.studentservice.domain.student.Lastname;
import amb.mat.school.life.studentservice.domain.student.StudentId;
import jakarta.annotation.Nullable;

public record UpdateStudentCommand(
        StudentId studentId,
        @Nullable Lastname lastname,
        @Nullable Firstname firstname,
        @Nullable Birthdate birthdate
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to update a student, %s";

    public UpdateStudentCommand {
        checkStudentIdMandatory(studentId);
    }

    private void checkStudentIdMandatory(StudentId studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the identifier is required"));
        }
    }
}