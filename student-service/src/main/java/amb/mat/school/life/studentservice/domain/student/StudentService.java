package amb.mat.school.life.studentservice.domain.student;

import amb.mat.school.life.studentservice.domain.student.command.CreateStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.DeleteStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

public class StudentService {

    private final StudentRepositoryPort studentRepositoryPort;

    public StudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
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
        Student student = studentRepositoryPort.get(command.studentId())
                .orElseThrow(() -> new NoSuchElementException("student not found with identifier %s".formatted(command.studentId().value())));
        if (student.updateInformation(command)) {
            studentRepositoryPort.put(student);
        }
    }

    @Transactional
    public void deleteStudent(DeleteStudentCommand command) {

    }
}
