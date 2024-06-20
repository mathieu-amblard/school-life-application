package amb.mat.school.life.student.persistence;

import amb.mat.school.life.student.domain.student.*;
import amb.mat.school.life.student.domain.user.Username;

public class StudentEntityMapper {

    public Student mapToDomain(StudentEntity studentEntity) {
        return new Student(
                new StudentId(studentEntity.studentId()),
                new Username(studentEntity.username()),
                new Lastname(studentEntity.lastname()),
                new Firstname(studentEntity.firstname()),
                new Birthdate(studentEntity.birthdate())
        );
    }

    public StudentEntity mapToEntity(Long id, Student student) {
        return new StudentEntity(
                id,
                student.studentId().value(),
                student.username().value(),
                student.lastname().value(),
                student.firstname().value(),
                student.birthdate().value()
        );
    }
}
