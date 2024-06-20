package amb.mat.school.life.student.domain.student.query;

import amb.mat.school.life.student.domain.student.StudentId;

/**
 * Query used to find a student
 *
 * @param studentId the identifier of the student to find
 */
public record FindStudentQuery(
        StudentId studentId
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to find a student, %s";

    public FindStudentQuery {
        checkStudentIdMandatory(studentId);
    }

    private void checkStudentIdMandatory(StudentId studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the identifier of the student is required"));
        }
    }
}
