package amb.mat.school.life.teacher.application;

import amb.mat.school.life.teacher.application.command.CreateTeacherAndUserCommand;
import amb.mat.school.life.teacher.domain.teacher.Teacher;
import amb.mat.school.life.teacher.domain.teacher.TeacherService;
import amb.mat.school.life.teacher.domain.teacher.command.CreateTeacherCommand;
import amb.mat.school.life.teacher.domain.teacher.command.DeleteTeacherCommand;
import amb.mat.school.life.teacher.domain.teacher.command.UpdateTeacherCommand;
import amb.mat.school.life.teacher.domain.teacher.query.FindAllTeachersQuery;
import amb.mat.school.life.teacher.domain.user.User;
import amb.mat.school.life.teacher.domain.user.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Facade + Orchestrator of the different domains (aka bounded contexts)
 */
public class TeacherApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherApplicationService.class);

    private final TeacherService teacherService;
    private final UserRepositoryPort userRepositoryPort;

    public TeacherApplicationService(TeacherService teacherService, UserRepositoryPort userRepositoryPort) {
        this.teacherService = teacherService;
        this.userRepositoryPort = userRepositoryPort;
    }

    public List<Teacher> getTeachers(FindAllTeachersQuery query) {
        return this.teacherService.getTeachers();
    }

    // No @Transactional here + Saga
    // Because we do not how much time the connection to the database will stay opened
    // Due to the remote service call...
    // See https://youtu.be/exqfB1WaqIw
    public Teacher createTeacher(CreateTeacherAndUserCommand command) {
        // As teacher and teacher domains are two different bounded contexts
        // We should ensure that the username does not already exist in the user bounded context
        // And not only in the teacher bounded context
        if (userRepositoryPort.get(command.username()).isPresent()) {
            throw new IllegalArgumentException(
                    "the username %s already exists, could not create teacher"
                            .formatted(command.username().value())
            );
        }
        CreateTeacherCommand createTeacherCommand = new CreateTeacherCommand(
                command.username(),
                command.lastname(),
                command.firstname(),
                command.resume()
        );
        Teacher teacher = teacherService.createTeacher(createTeacherCommand);
        User user = new User(command.username(), command.emailAddress());
        try {
            userRepositoryPort.put(user, command.password());
        } catch (Exception exception) {
            // Saga like
            // In case of exception, must compensate...
            // As it is easier to compensate the current domain, calls are made in this order
            // Ideally, we should compensate using an event mechanism in order to be able to retry asynchronously
            // In case of the compensation fails also
            teacherService.deleteTeacher(new DeleteTeacherCommand(teacher.teacherId()));
            throw new RuntimeException(exception); // TODO Throw a dedicated exception
        }
        return teacher;
    }

    // Not needed to introduce another command as here we only act as a proxy of the TeacherService
    // i.e. there is no need to call other services
    public void updateTeacher(UpdateTeacherCommand command) {
        teacherService.updateTeacher(command);
    }

    // No @Transactional here + Saga
    public void deleteTeacher(DeleteTeacherCommand command) {
        teacherService.deleteTeacher(command)
                .ifPresent(teacher -> {
                    try {
                        userRepositoryPort.delete(teacher.username());
                    } catch (Exception exception) {
                        CreateTeacherCommand createTeacherCommand = new CreateTeacherCommand(
                                teacher.username(),
                                teacher.lastname(),
                                teacher.firstname(),
                                teacher.resume()
                        );
                        teacherService.createTeacher(createTeacherCommand);
                        throw new RuntimeException(exception); // TODO Throw a dedicated exception
                    }
                });
    }
}
