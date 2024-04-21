package amb.mat.school.life.studentservice.config;

import amb.mat.school.life.studentservice.domain.student.StudentRepositoryPort;
import amb.mat.school.life.studentservice.domain.student.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainConfiguration {

    @Bean
    StudentService studentService(StudentRepositoryPort studentRepositoryPort) {
        return new StudentService(studentRepositoryPort);
    }
}
