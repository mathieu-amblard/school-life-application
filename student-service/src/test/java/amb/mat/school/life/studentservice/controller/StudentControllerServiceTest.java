package amb.mat.school.life.studentservice.controller;

import amb.mat.school.life.studentservice.config.SecurityConfiguration;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class StudentControllerServiceTest {

    private static final String URL_PATTERN = "http://localhost:%d/api/students%s";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    // TODO find a way to security from service test
    // Trick to exclude security configuration
    @MockBean
    private SecurityConfiguration securityConfiguration;

    @DynamicPropertySource
    static void springProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("app.client.user.url", () -> "http://localhost:${wiremock.server.port}");
    }

    @Test
    @WithMockUser(username = "test")
    void should_create_new_student() {
        // Given
        RequestSpecification requestSpecification = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body("""
                        {
                            "username": "username",
                            "password": "password",
                            "email-address": "username@student.com",
                            "lastname": "Lastname",
                            "firstname": "Firstname",
                            "birthdate": "2021-10-23"
                        }
                        """);
        String url = URL_PATTERN.formatted(port, "");
        // When
        Response response = requestSpecification.when()
                .post(url);
        // Then
        response.then()
                .statusCode(201);
    }
}