package amb.mat.school.life.student.config;

import amb.mat.school.life.student.application.StudentApplicationService;
import amb.mat.school.life.student.domain.student.StudentService;
import amb.mat.school.life.student.domain.user.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfiguration {

    @Bean
    StudentApplicationService studentApplicationService(
            StudentService studentService,
            UserRepositoryPort userRepositoryPort
    ) {
        return new StudentApplicationService(studentService, userRepositoryPort);
    }
}
