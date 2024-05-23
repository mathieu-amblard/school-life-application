package amb.mat.school.life.teacherservice.persistence;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface TeacherJdbcRepository extends ListCrudRepository<TeacherEntity, Long> {

    Optional<TeacherEntity> findByTeacherId(String teacherId);

    Optional<TeacherEntity> findByUsername(String username);
}
