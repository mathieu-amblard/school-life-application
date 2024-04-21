package amb.mat.school.life.studentservice.application;

import amb.mat.school.life.studentservice.application.command.CreateStudentAndUserCommand;
import amb.mat.school.life.studentservice.domain.student.Student;
import amb.mat.school.life.studentservice.domain.student.StudentService;
import amb.mat.school.life.studentservice.domain.student.command.CreateStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.DeleteStudentCommand;
import amb.mat.school.life.studentservice.domain.student.command.UpdateStudentCommand;
import amb.mat.school.life.studentservice.domain.user.User;
import amb.mat.school.life.studentservice.domain.user.UserRepositoryPort;

/**
 * Facade + Orchestrator of the different domains (aka bounded contexts)
 */
public class StudentApplicationService {

    private final StudentService studentService;
    private final UserRepositoryPort userRepositoryPort;

    public StudentApplicationService(StudentService studentService, UserRepositoryPort userRepositoryPort) {
        this.studentService = studentService;
        this.userRepositoryPort = userRepositoryPort;
    }

    // No @Transactional here
    // Because we do not how much time the connection to the database will stay opened
    // Due to the remote service call...
    // See https://youtu.be/exqfB1WaqIw
    public Student createStudent(CreateStudentAndUserCommand command) {
        // As teacher and student domains are two different bounded contexts
        // We should ensure that the username does not already exist in the user bounded context
        // And not only in the student bounded context
        if (userRepositoryPort.get(command.username()).isPresent()) {
            throw new IllegalArgumentException(
                    "the username %s already exists, could not create student"
                            .formatted(command.username().value())
            );
        }
        CreateStudentCommand createStudentCommand = new CreateStudentCommand(
                command.username(),
                command.lastname(),
                command.firstname(),
                command.birthdate()
        );
        Student student = studentService.createStudent(createStudentCommand);
        User user = new User(command.username(), command.emailAddress());
        try {
            userRepositoryPort.put(user, command.password());
        } catch (Exception exception) {
            // Saga like
            // In case of exception, must compensate...
            // As it is easier to compensate the current domain, calls are made in this order
            // Ideally, we should compensate using an event mechanism in order to be able to retry asynchronously
            // In case of the compensation fails also
            studentService.deleteStudent(new DeleteStudentCommand(student.studentId()));
        }
        return student;
    }

    // Not needed to introduce another command as here we only act as a proxy of the StudentService
    // i.e. there is no need to call other services
    public void updateStudent(UpdateStudentCommand command) {
        studentService.updateStudent(command);
    }
}
