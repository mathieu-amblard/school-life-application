package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.domain.PasswordEncoderPort;
import amb.mat.school.life.user.management.domain.UserRepositoryPort;
import amb.mat.school.life.user.management.domain.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DomainConfiguration {

    @Bean
    UserService userService(UserRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        return new UserService(userRepository, passwordEncoder);
    }
}
