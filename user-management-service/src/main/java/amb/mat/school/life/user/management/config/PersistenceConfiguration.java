package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.persistence.*;
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
    UserRepositoryAdapter userRepositoryAdapter(
            UserJdbcRepository userJdbcRepository,
            UserEntityMapper userEntityMapper
    ) {
        return new UserRepositoryAdapter(userJdbcRepository, userEntityMapper);
    }

    @Bean
    PasswordEncoderAdapter passwordEncoderAdapter() {
        return new PasswordEncoderAdapter();
    }
}
