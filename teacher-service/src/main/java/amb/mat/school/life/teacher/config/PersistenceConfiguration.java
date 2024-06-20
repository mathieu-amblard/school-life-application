package amb.mat.school.life.teacher.config;

import amb.mat.school.life.teacher.persistence.TeacherEntity;
import amb.mat.school.life.teacher.persistence.TeacherRepositoryAdapter;
import amb.mat.school.life.teacher.persistence.TeacherEntityMapper;
import amb.mat.school.life.teacher.persistence.TeacherJdbcRepository;
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
