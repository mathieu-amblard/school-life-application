package amb.mat.school.life.studentservice.config;

import amb.mat.school.life.studentservice.client.UserClient;
import amb.mat.school.life.studentservice.client.UserRepositoryAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
        RestTemplate restTemplate = restTemplateBuilder
                .additionalInterceptors((request, body, execution) -> {
                    // TODO find a better way to propagate the token, ideally the Gateway should act as the single entry point
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication.isAuthenticated() && authentication instanceof JwtAuthenticationToken authenticationToken) {
                        String tokenValue = authenticationToken.getToken().getTokenValue();
                        request.getHeaders().add("Authorization", "Bearer " + tokenValue);
                    }
                    return execution.execute(request, body);
                })
                .build();
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
