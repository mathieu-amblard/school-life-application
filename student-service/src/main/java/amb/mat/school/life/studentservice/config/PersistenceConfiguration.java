package amb.mat.school.life.studentservice.config;

import amb.mat.school.life.studentservice.persistence.StudentEntity;
import amb.mat.school.life.studentservice.persistence.StudentEntityMapper;
import amb.mat.school.life.studentservice.persistence.StudentJdbcRepository;
import amb.mat.school.life.studentservice.persistence.StudentRepositoryAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackageClasses = StudentEntity.class)
@EnableJdbcRepositories(basePackageClasses = StudentJdbcRepository.class)
public class PersistenceConfiguration {

    @Bean
    StudentEntityMapper studentEntityMapper() {
        return new StudentEntityMapper();
    }

    @Bean
    StudentRepositoryAdapter studentRepositoryAdapter(
            StudentJdbcRepository studentJdbcRepository,
            StudentEntityMapper studentEntityMapper
    ) {
        return new StudentRepositoryAdapter(studentJdbcRepository, studentEntityMapper);
    }
}
