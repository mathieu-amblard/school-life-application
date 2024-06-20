package amb.mat.school.life.teacher.domain.teacher;

import amb.mat.school.life.teacher.domain.Identifier;

import java.util.List;
import java.util.Optional;

public interface TeacherRepositoryPort {

    List<Teacher> getAll();

    Optional<Teacher> get(Identifier identifier);

    void put(Teacher teacher);

    Optional<Teacher> remove(Identifier teacherId);
}
