package amb.mat.school.life.student.persistence;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface StudentJdbcRepository extends ListCrudRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByStudentId(String studentId);

    Optional<StudentEntity> findByUsername(String username);
}
