package amb.mat.school.life.studentservice.controller;

import amb.mat.school.life.studentservice.persistence.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
class StudentControllerIT {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void springProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("app.client.user.url", () -> "http://localhost:${wiremock.server.port}");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DELETE FROM students");
    }

    @Test
    void should_get_whoami() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc
                .perform(get("/api/students/whoami")
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "student"))))
                );
        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    void should_create_new_student() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc
                .perform(post("/api/students")
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "username": "username",
                                            "password": "password",
                                            "emailAddress": "username@student.com",
                                            "lastname": "Lastname",
                                            "firstname": "Firstname",
                                            "birthdate": "2021-10-23"
                                        }
                                        """
                        )
                );
        // Then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        """
                                {
                                    "username": "username",
                                    "emailAddress": "username@student.com",
                                    "lastname": "Lastname",
                                    "firstname": "Firstname",
                                    "birthdate": "2021-10-23"
                                }
                                """
                ));
        Optional<StudentEntity> optStudentEntity = findStudentByUsername("username");
        assertThat(optStudentEntity)
                .isPresent()
                .hasValueSatisfying(studentEntity -> {
                    assertThat(studentEntity.id()).isPositive();
                    assertThat(studentEntity.studentId()).isNotBlank();
                    assertThat(studentEntity.username()).isEqualTo("username");
                    assertThat(studentEntity.lastname()).isEqualTo("Lastname");
                    assertThat(studentEntity.firstname()).isEqualTo("Firstname");
                    assertThat(studentEntity.birthdate()).isEqualTo(LocalDate.of(2021, 10, 23));
                });
    }

    @Test
    void should_patch_existing_student() throws Exception {
        // Given
        String username = "username";
        String studentId = insertStudent(username);
        // When
        ResultActions resultActions = mockMvc
                .perform(patch("/api/students/{studentId}", studentId)
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "lastname": "Shelby",
                                            "firstname": "Thomas",
                                            "birthdate": "2018-06-27"
                                        }
                                        """
                        )
                );
        // Then
        resultActions.andExpect(status().isNoContent());
        Optional<StudentEntity> optStudentEntity = findStudentByUsername(username);
        assertThat(optStudentEntity)
                .isPresent()
                .hasValueSatisfying(studentEntity -> {
                    assertThat(studentEntity.id()).isPositive();
                    assertThat(studentEntity.studentId()).isEqualTo(studentId);
                    assertThat(studentEntity.username()).isEqualTo(username);
                    assertThat(studentEntity.lastname()).isEqualTo("Shelby");
                    assertThat(studentEntity.firstname()).isEqualTo("Thomas");
                    assertThat(studentEntity.birthdate()).isEqualTo(LocalDate.of(2018, 6, 27));
                });
    }

    @Test
    void should_delete_existing_student() throws Exception {
        // Given
        String username = "username";
        String studentId = insertStudent(username);
        // When
        ResultActions resultActions = mockMvc
                .perform(delete("/api/students/{studentId}", studentId)
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                );
        // Then
        resultActions.andExpect(status().isNoContent());
        Optional<StudentEntity> studentEntity = findStudentByUsername(username);
        assertThat(studentEntity).isEmpty();
    }

    private String insertStudent(String username) {
        String studentId = UUID.randomUUID().toString();
        jdbcTemplate.execute("""
                INSERT INTO students (student_id, username, lastname, firstname, birthdate)
                VALUES ('%s', '%s', 'Lastname', 'Firstname', '2021-10-23')
                """.formatted(studentId, username)
        );
        return studentId;
    }

    private Optional<StudentEntity> findStudentByUsername(String username) {
        return jdbcTemplate.queryForList(
                "SELECT * FROM students WHERE username = '%s'".formatted(username),
                StudentEntity.class
        ).stream().findFirst();
    }
}