package amb.mat.school.life.user.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username,password,true from users where username = ?");
        manager.setAuthoritiesByUsernameQuery("select username,authority from authorities where username = ?");
        return manager;
    }
//
//    @Bean
//    RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient userManagementService = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("user-management-service")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .scope(OidcScopes.OPENID)
//                .scope("user.write")
//                .scope("user.read")
//                .redirectUri("http://localhost:8080/api/user-accounts")
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).requireProofKey(true).build())
//                .build();
//        return new InMemoryRegisteredClientRepository(userManagementService);
//    }
//
//    @Bean
//    @Order(1)
//    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
//        http
//                .exceptionHandling(exceptions -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }
//
//    @Bean
//    @Order(2)
//    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/api/**")
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.GET).hasAuthority("SCOPE_user.read")
//                        .anyRequest().hasAuthority("SCOPE_user.write")
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }
//
//    @Bean
//    @Order(3)
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
//                .csrf(CsrfConfigurer::disable);
//        return http.build();
//    }
}
