package amb.mat.school.life.user.management.config;

import amb.mat.school.life.user.management.controller.UserAccountDtoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ControllerConfiguration {

    /**
     * The main advantage of not annotate the mapper with @Component or @Service
     * is that you will not be confused when testing controllers...
     * As the dependency does not exist without this configuration, you will automatically know that you have to use @MockBean.
     * On the other side, as the dependency is present you can simply use @SpringBootTest and inject it with @Autowired if needed.
     * <p>
     * The goal is really to simplify the testing strategy and put some simple rules on top of it.
     */
    @Bean
    UserAccountDtoMapper userAccountDtoMapper() {
        return new UserAccountDtoMapper();
    }
}
