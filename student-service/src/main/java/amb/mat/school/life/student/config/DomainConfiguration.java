package amb.mat.school.life.student.config;

import amb.mat.school.life.student.domain.student.StudentRepositoryPort;
import amb.mat.school.life.student.domain.student.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainConfiguration {

    @Bean
    StudentService studentService(StudentRepositoryPort studentRepositoryPort) {
        return new StudentService(studentRepositoryPort);
    }
}
