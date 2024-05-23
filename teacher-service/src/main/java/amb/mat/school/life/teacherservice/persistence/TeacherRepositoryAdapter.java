package amb.mat.school.life.teacherservice.persistence;

import amb.mat.school.life.teacherservice.domain.Identifier;
import amb.mat.school.life.teacherservice.domain.teacher.Teacher;
import amb.mat.school.life.teacherservice.domain.teacher.TeacherId;
import amb.mat.school.life.teacherservice.domain.teacher.TeacherRepositoryPort;
import amb.mat.school.life.teacherservice.domain.user.Username;

import java.util.List;
import java.util.Optional;

public class TeacherRepositoryAdapter implements TeacherRepositoryPort {

    private final TeacherJdbcRepository teacherJdbcRepository;
    private final TeacherEntityMapper teacherEntityMapper;

    public TeacherRepositoryAdapter(TeacherJdbcRepository teacherJdbcRepository, TeacherEntityMapper teacherEntityMapper) {
        this.teacherJdbcRepository = teacherJdbcRepository;
        this.teacherEntityMapper = teacherEntityMapper;
    }

    @Override
    public List<Teacher> getAll() {
        return teacherJdbcRepository.findAll()
                .stream()
                .map(teacherEntityMapper::mapToDomain)
                .toList();
    }

    @Override
    public Optional<Teacher> get(Identifier identifier) {
        return getEntity(identifier).map(teacherEntityMapper::mapToDomain);
    }

    @Override
    public void put(Teacher teacher) {
        Long id = getEntity(teacher.teacherId())
                .map(TeacherEntity::id)
                .orElse(null);
        TeacherEntity teacherEntity = teacherEntityMapper.mapToEntity(id, teacher);
        teacherJdbcRepository.save(teacherEntity);
    }

    @Override
    public Optional<Teacher> remove(Identifier identifier) {
        Optional<TeacherEntity> optEntity = getEntity(identifier);
        optEntity.ifPresent(teacherJdbcRepository::delete);
        return optEntity.map(teacherEntityMapper::mapToDomain);
    }

    private Optional<TeacherEntity> getEntity(Identifier identifier) {
        return switch (identifier) {
            case TeacherId(var value) -> teacherJdbcRepository.findByTeacherId(value);
            case Username(var value) -> teacherJdbcRepository.findByUsername(value);
            default -> throw new IllegalStateException("Unexpected value: " + identifier);
        };
    }
}
