package amb.mat.school.life.studentservice.controller;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
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
class StudentControllerTest {

    private static final String URL_PATTERN = "http://localhost:%d/students%s";

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void springProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("app.client.user.url", () -> "http://localhost:${wiremock.server.port}");
    }

    @Test
    @WithMockUser(username = "admin", roles = "admin")
    void should_create_new_student() {
        // Given
        RequestSpecification requestSpecification = given()
                .body("""
                        {
                            "username": "username",
                            "password": "password",
                            "email-address": "username@student.com",
                            "lastname": "Lastname",
                            "firstname": "Firstname",
                            "birthdate": "23/10/2021"
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