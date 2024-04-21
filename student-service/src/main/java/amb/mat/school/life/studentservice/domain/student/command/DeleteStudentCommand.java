package amb.mat.school.life.studentservice.domain.student.command;

import amb.mat.school.life.studentservice.domain.student.StudentId;

public record DeleteStudentCommand(
        StudentId studentId
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to delete a student, %s";

    public DeleteStudentCommand {
        checkStudentIdMandatory(studentId);
    }

    private void checkStudentIdMandatory(StudentId studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the identifier is required"));
        }
    }
}
