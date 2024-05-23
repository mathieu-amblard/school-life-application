package amb.mat.school.life.teacherservice.domain.teacher;

import amb.mat.school.life.teacherservice.domain.teacher.command.CreateTeacherCommand;
import amb.mat.school.life.teacherservice.domain.teacher.command.DeleteTeacherCommand;
import amb.mat.school.life.teacherservice.domain.teacher.command.UpdateTeacherCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TeacherService {

    private final TeacherRepositoryPort teacherRepositoryPort;

    public TeacherService(TeacherRepositoryPort teacherRepositoryPort) {
        this.teacherRepositoryPort = teacherRepositoryPort;
    }

    @Transactional(readOnly = true)
    public List<Teacher> getTeachers() {
        return teacherRepositoryPort.getAll();
    }

    /**
     * Create a new teacher
     *
     * @param command the {@link CreateTeacherCommand} to use
     */
    @Transactional
    public Teacher createTeacher(CreateTeacherCommand command) {
        Teacher teacher = new Teacher(command);
        teacherRepositoryPort.put(teacher);
        return teacher;
    }

    /**
     * Update an existing user
     *
     * @param command the {@link UpdateTeacherCommand} to use
     */
    @Transactional
    public void updateTeacher(UpdateTeacherCommand command) {
        Teacher teacher = teacherRepositoryPort.get(command.identifier())
                .orElseThrow(() -> new NoSuchElementException("teacher not found with identifier %s".formatted(command.identifier().value())));
        if (teacher.updateInformation(command)) {
            teacherRepositoryPort.put(teacher);
        }
    }

    @Transactional
    public Optional<Teacher> deleteTeacher(DeleteTeacherCommand command) {
        return teacherRepositoryPort.remove(command.identifier());
    }
}
