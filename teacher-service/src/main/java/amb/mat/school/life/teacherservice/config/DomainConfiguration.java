package amb.mat.school.life.teacherservice.config;

import amb.mat.school.life.teacherservice.domain.teacher.TeacherRepositoryPort;
import amb.mat.school.life.teacherservice.domain.teacher.TeacherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainConfiguration {

    @Bean
    TeacherService teacherService(TeacherRepositoryPort teacherRepositoryPort) {
        return new TeacherService(teacherRepositoryPort);
    }
}
