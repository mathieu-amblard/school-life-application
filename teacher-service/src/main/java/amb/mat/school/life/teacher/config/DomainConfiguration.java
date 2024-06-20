package amb.mat.school.life.teacher.config;

import amb.mat.school.life.teacher.domain.teacher.TeacherService;
import amb.mat.school.life.teacher.domain.teacher.TeacherRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainConfiguration {

    @Bean
    TeacherService teacherService(TeacherRepositoryPort teacherRepositoryPort) {
        return new TeacherService(teacherRepositoryPort);
    }
}
