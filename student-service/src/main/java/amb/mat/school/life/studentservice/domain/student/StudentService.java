package amb.mat.school.life.studentservice.domain.student;

import amb.mat.school.life.studentservice.domain.student.command.CreateStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Create a new student
     *
     * @param command the {@link CreateStudentCommand} to use
     */
    @Transactional
    public void createStudent(CreateStudentCommand command) {
        Student student = new Student(command);
        studentRepository.put(student);
    }

    /**
     * Update an existing user
     *
     * @param command the {@link UpdateStudentCommand} to use
     */
    @Transactional
    public void updateStudent(UpdateStudentCommand command) {
        Student student = studentRepository.findByStudentId(command.studentId())
                .orElseThrow(() -> new NoSuchElementException("student not found with identifier %s".formatted(command.studentId().value())));
        if (student.updateInformation(command)) {
            studentRepository.put(student);
        }
    }
}
