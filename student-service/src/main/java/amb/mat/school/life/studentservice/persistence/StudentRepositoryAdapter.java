package amb.mat.school.life.studentservice.persistence;

import amb.mat.school.life.studentservice.domain.student.Student;
import amb.mat.school.life.studentservice.domain.student.StudentId;
import amb.mat.school.life.studentservice.domain.student.StudentRepositoryPort;

import java.util.Optional;

public class StudentRepositoryAdapter implements StudentRepositoryPort {

    private final StudentJdbcRepository studentJdbcRepository;
    private final StudentEntityMapper studentEntityMapper;

    public StudentRepositoryAdapter(StudentJdbcRepository studentJdbcRepository, StudentEntityMapper studentEntityMapper) {
        this.studentJdbcRepository = studentJdbcRepository;
        this.studentEntityMapper = studentEntityMapper;
    }

    @Override
    public Optional<Student> get(StudentId studentId) {
        return studentJdbcRepository.findByStudentId(studentId.value())
                .map(studentEntityMapper::mapToDomain);
    }

    @Override
    public void put(Student student) {
        Long id = studentJdbcRepository.findByStudentId(student.studentId().value())
                .map(StudentEntity::id)
                .orElse(null);
        StudentEntity studentEntity = studentEntityMapper.mapToEntity(id, student);
        studentJdbcRepository.save(studentEntity);
    }

    @Override
    public void remove(StudentId studentId) {
        studentJdbcRepository.findByStudentId(studentId.value())
                .ifPresent(studentJdbcRepository::delete);
    }
}
