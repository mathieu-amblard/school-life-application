package amb.mat.school.life.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                .route(route -> route
                        .path("/user/**")
                        .filters(filter -> filter
                                .rewritePath("/user/(?<segment>.*)", "/${segment}")
                                .tokenRelay()
                        )
                        .uri("http://localhost:8081")
                )
                .route(route -> route
                        .path("/student/**")
                        .filters(filter -> filter
                                .rewritePath("/student/(?<segment>.*)", "/${segment}")
                                .tokenRelay()
                        )
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
