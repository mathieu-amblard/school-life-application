package amb.mat.school.life.studentservice.config;

import amb.mat.school.life.studentservice.client.UserClient;
import amb.mat.school.life.studentservice.client.UserRepositoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration(proxyBeanMethods = false)
public class ClientConfiguration {

    @Bean
    UserClient userClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${app.client.user.url}") String url
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(url));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserClient.class);
    }

    @Bean
    UserRepositoryAdapter userRepositoryAdapter(UserClient userClient) {
        return new UserRepositoryAdapter(userClient);
    }
}
