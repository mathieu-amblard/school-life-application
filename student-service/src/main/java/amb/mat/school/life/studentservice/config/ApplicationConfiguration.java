package amb.mat.school.life.studentservice.config;

import amb.mat.school.life.studentservice.application.StudentApplicationService;
import amb.mat.school.life.studentservice.domain.student.StudentService;
import amb.mat.school.life.studentservice.domain.user.UserRepositoryPort;
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
