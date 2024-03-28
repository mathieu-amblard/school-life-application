package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.domain.UserAccountRepository;
import amb.mat.school.life.user.management.persistence.UserAccountServiceAdapter;
import amb.mat.school.life.user.management.persistence.jdbc.UserAccountEntity;
import amb.mat.school.life.user.management.persistence.jdbc.UserAccountEntityMapper;
import amb.mat.school.life.user.management.persistence.jdbc.UserAccountJdbcRepository;
import amb.mat.school.life.user.management.persistence.jdbc.UserAccountRepositoryAdapter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration(proxyBeanMethods = false)
@EntityScan(basePackageClasses = UserAccountEntity.class)
@EnableJdbcRepositories(basePackageClasses = UserAccountJdbcRepository.class)
public class PersistenceConfiguration {

    @Bean
    UserAccountEntityMapper userEntityMapper() {
        return new UserAccountEntityMapper();
    }

    @Bean
    UserAccountRepositoryAdapter userAccountRepository(UserAccountJdbcRepository userAccountJdbcRepository, UserAccountEntityMapper userAccountEntityMapper) {
        return new UserAccountRepositoryAdapter(userAccountJdbcRepository, userAccountEntityMapper);
    }

    @Bean
    UserAccountServiceAdapter userAccountService(UserAccountRepository userAccountRepository) {
        return new UserAccountServiceAdapter(userAccountRepository);
    }
}
