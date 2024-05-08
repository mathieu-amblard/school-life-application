package amb.mat.school.life.studentservice.domain.student;

import amb.mat.school.life.studentservice.domain.Identifier;

import java.util.List;
import java.util.Optional;

public interface StudentRepositoryPort {

    List<Student> getAll();

    Optional<Student> get(Identifier identifier);

    void put(Student student);

    Optional<Student> remove(Identifier studentId);
}
