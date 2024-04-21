package amb.mat.school.life.studentservice.domain.student;

import java.util.Optional;

public interface StudentRepositoryPort {

    Optional<Student> get(StudentId studentId);

    void put(Student student);

    void remove(StudentId studentId);
}
