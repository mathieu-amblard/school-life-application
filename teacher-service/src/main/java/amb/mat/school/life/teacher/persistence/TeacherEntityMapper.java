package amb.mat.school.life.teacher.persistence;

import amb.mat.school.life.teacher.domain.teacher.*;
import amb.mat.school.life.teacher.domain.user.Username;

public class TeacherEntityMapper {

    public Teacher mapToDomain(TeacherEntity teacherEntity) {
        return new Teacher(
                new TeacherId(teacherEntity.teacherId()),
                new Username(teacherEntity.username()),
                new Lastname(teacherEntity.lastname()),
                new Firstname(teacherEntity.firstname()),
                new Resume(teacherEntity.resume())
        );
    }

    public TeacherEntity mapToEntity(Long id, Teacher teacher) {
        return new TeacherEntity(
                id,
                teacher.teacherId().value(),
                teacher.username().value(),
                teacher.lastname().value(),
                teacher.firstname().value(),
                teacher.resume().value()
        );
    }
}
