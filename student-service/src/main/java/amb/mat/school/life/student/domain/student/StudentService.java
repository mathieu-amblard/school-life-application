package amb.mat.school.life.student.domain.student;

import amb.mat.school.life.student.domain.student.command.CreateStudentCommand;
import amb.mat.school.life.student.domain.student.command.DeleteStudentCommand;
import amb.mat.school.life.student.domain.student.command.UpdateStudentCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class StudentService {

    private final StudentRepositoryPort studentRepositoryPort;

    public StudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
    }

    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        return studentRepositoryPort.getAll();
    }

    /**
     * Create a new student
     *
     * @param command the {@link CreateStudentCommand} to use
     */
    @Transactional
    public Student createStudent(CreateStudentCommand command) {
        Student student = new Student(command);
        studentRepositoryPort.put(student);
        return student;
    }

    /**
     * Update an existing user
     *
     * @param command the {@link UpdateStudentCommand} to use
     */
    @Transactional
    public void updateStudent(UpdateStudentCommand command) {
        Student student = studentRepositoryPort.get(command.identifier())
                .orElseThrow(() -> new NoSuchElementException("student not found with identifier %s".formatted(command.identifier().value())));
        if (student.updateInformation(command)) {
            studentRepositoryPort.put(student);
        }
    }

    @Transactional
    public Optional<Student> deleteStudent(DeleteStudentCommand command) {
        return studentRepositoryPort.remove(command.identifier());
    }
}
