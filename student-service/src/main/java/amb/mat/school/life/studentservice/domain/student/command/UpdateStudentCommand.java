package amb.mat.school.life.studentservice.domain.student.command;

import amb.mat.school.life.studentservice.domain.student.Firstname;
import amb.mat.school.life.studentservice.domain.student.Lastname;
import amb.mat.school.life.studentservice.domain.student.StudentId;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UpdateStudentCommand(
        StudentId studentId,
        @Nullable Lastname lastname,
        @Nullable Firstname firstname,
        @Nullable LocalDate birthdate
) {

    public UpdateStudentCommand {
        checkStudentIdMandatory(studentId);
    }

    private void checkStudentIdMandatory(StudentId studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("student identifier must not be null");
        }
    }
}