package amb.mat.school.life.student.persistence;

import amb.mat.school.life.student.domain.student.Student;
import amb.mat.school.life.student.domain.student.StudentId;
import amb.mat.school.life.student.domain.user.Username;
import amb.mat.school.life.student.domain.Identifier;
import amb.mat.school.life.student.domain.student.StudentRepositoryPort;

import java.util.List;
import java.util.Optional;

public class StudentRepositoryAdapter implements StudentRepositoryPort {

    private final StudentJdbcRepository studentJdbcRepository;
    private final StudentEntityMapper studentEntityMapper;

    public StudentRepositoryAdapter(StudentJdbcRepository studentJdbcRepository, StudentEntityMapper studentEntityMapper) {
        this.studentJdbcRepository = studentJdbcRepository;
        this.studentEntityMapper = studentEntityMapper;
    }

    @Override
    public List<Student> getAll() {
        return studentJdbcRepository.findAll()
                .stream()
                .map(studentEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Student> get(Identifier identifier) {
        return getEntity(identifier).map(studentEntityMapper::mapToDomain);
    }

    @Override
    public void put(Student student) {
        Long id = getEntity(student.studentId())
                .map(StudentEntity::id)
                .orElse(null);
        StudentEntity studentEntity = studentEntityMapper.mapToEntity(id, student);
        studentJdbcRepository.save(studentEntity);
    }

    @Override
    public Optional<Student> remove(Identifier identifier) {
        Optional<StudentEntity> optEntity = getEntity(identifier);
        optEntity.ifPresent(studentJdbcRepository::delete);
        return optEntity.map(studentEntityMapper::mapToDomain);
    }

    private Optional<StudentEntity> getEntity(Identifier identifier) {
        return switch (identifier) {
            case StudentId(var value) -> studentJdbcRepository.findByStudentId(value);
            case Username(var value) -> studentJdbcRepository.findByUsername(value);
            default -> throw new IllegalStateException("Unexpected value: " + identifier);
        };
    }
}
