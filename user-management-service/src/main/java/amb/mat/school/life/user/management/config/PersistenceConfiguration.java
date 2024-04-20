package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.persistence.PasswordEncoderAdapter;
import amb.mat.school.life.user.management.persistence.jdbc.UserEntity;
import amb.mat.school.life.user.management.persistence.jdbc.UserEntityMapper;
import amb.mat.school.life.user.management.persistence.jdbc.UserJdbcRepository;
import amb.mat.school.life.user.management.persistence.jdbc.UserRepositoryAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackageClasses = UserEntity.class)
@EnableJdbcRepositories(basePackageClasses = UserJdbcRepository.class)
public class PersistenceConfiguration {

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }

    @Bean
    UserRepositoryAdapter userRepository(
            UserJdbcRepository userJdbcRepository,
            UserEntityMapper userEntityMapper
    ) {
        return new UserRepositoryAdapter(userJdbcRepository, userEntityMapper);
    }

    @Bean
    PasswordEncoderAdapter passwordEncoder() {
        return new PasswordEncoderAdapter();
    }
}
