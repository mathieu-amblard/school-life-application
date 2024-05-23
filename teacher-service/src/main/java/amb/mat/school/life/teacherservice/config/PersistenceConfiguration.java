package amb.mat.school.life.teacherservice.config;

import amb.mat.school.life.teacherservice.persistence.TeacherEntity;
import amb.mat.school.life.teacherservice.persistence.TeacherEntityMapper;
import amb.mat.school.life.teacherservice.persistence.TeacherJdbcRepository;
import amb.mat.school.life.teacherservice.persistence.TeacherRepositoryAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackageClasses = TeacherEntity.class)
@EnableJdbcRepositories(basePackageClasses = TeacherJdbcRepository.class)
public class PersistenceConfiguration {

    @Bean
    TeacherEntityMapper teacherEntityMapper() {
        return new TeacherEntityMapper();
    }

    @Bean
    TeacherRepositoryAdapter teacherRepositoryAdapter(
            TeacherJdbcRepository teacherJdbcRepository,
            TeacherEntityMapper teacherEntityMapper
    ) {
        return new TeacherRepositoryAdapter(teacherJdbcRepository, teacherEntityMapper);
    }
}
