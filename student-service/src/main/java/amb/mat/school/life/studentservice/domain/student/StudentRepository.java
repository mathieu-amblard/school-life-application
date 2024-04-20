package amb.mat.school.life.studentservice.domain.student;

import java.util.Optional;

public interface StudentRepository {

    void put(Student student);

    Optional<Student> findByStudentId(StudentId studentId);
}
