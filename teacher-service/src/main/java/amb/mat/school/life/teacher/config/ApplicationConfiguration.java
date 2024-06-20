package amb.mat.school.life.teacher.config;

import amb.mat.school.life.teacher.application.TeacherApplicationService;
import amb.mat.school.life.teacher.domain.teacher.TeacherService;
import amb.mat.school.life.teacher.domain.user.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfiguration {

    @Bean
    TeacherApplicationService teacherApplicationService(
            TeacherService teacherService,
            UserRepositoryPort userRepositoryPort
    ) {
        return new TeacherApplicationService(teacherService, userRepositoryPort);
    }
}
