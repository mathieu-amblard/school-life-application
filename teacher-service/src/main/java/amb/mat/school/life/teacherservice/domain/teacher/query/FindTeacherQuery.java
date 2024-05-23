package amb.mat.school.life.teacherservice.domain.teacher.query;

import amb.mat.school.life.teacherservice.domain.teacher.TeacherId;

/**
 * Query used to find a teacher
 *
 * @param teacherId the identifier of the teacher to find
 */
public record FindTeacherQuery(
        TeacherId teacherId
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to find a teacher, %s";

    public FindTeacherQuery {
        checkTeacherIdMandatory(teacherId);
    }

    private void checkTeacherIdMandatory(TeacherId teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the identifier of the teacher is required"));
        }
    }
}
