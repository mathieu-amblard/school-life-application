package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.persistence.UserAccountServiceAdapter;
import amb.mat.school.life.user.management.persistence.jdbc.UserAccountRepositoryAdapter;
import amb.mat.school.life.user.management.persistence.jdbc.UserEntity;
import amb.mat.school.life.user.management.persistence.jdbc.UserEntityMapper;
import amb.mat.school.life.user.management.persistence.jdbc.UserJdbcRepository;
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
    UserAccountRepositoryAdapter userAccountRepository(UserJdbcRepository userJdbcRepository, UserEntityMapper userEntityMapper) {
        return new UserAccountRepositoryAdapter(userJdbcRepository, userEntityMapper);
    }

    @Bean
    UserAccountServiceAdapter userAccountService(UserAccountRepository userAccountRepository) {
        return new UserAccountServiceAdapter(userAccountRepository);
    }
}
