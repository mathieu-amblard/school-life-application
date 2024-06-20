package amb.mat.school.life.teacher.controller;

import amb.mat.school.life.teacher.persistence.TeacherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.DataClassRowMapper;
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
class TeacherControllerIT {

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
        jdbcTemplate.execute("DELETE FROM teachers");
    }

    @Test
    void should_get_whoami() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc
                .perform(get("/api/teachers/whoami")
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "teacher"))))
                );
        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    void should_create_new_teacher() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc
                .perform(post("/api/teachers")
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "username": "username",
                                            "password": "password",
                                            "emailAddress": "username@teacher.com",
                                            "lastname": "Lastname",
                                            "firstname": "Firstname",
                                            "resume": "Core Qualifications : ..."
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
                                    "emailAddress": "username@teacher.com",
                                    "lastname": "Lastname",
                                    "firstname": "Firstname",
                                    "resume": "Core Qualifications : ..."
                                }
                                """
                ));
        Optional<TeacherEntity> optTeacherEntity = findTeacherByUsername("username");
        assertThat(optTeacherEntity)
                .isPresent()
                .hasValueSatisfying(teacherEntity -> {
                    assertThat(teacherEntity.id()).isPositive();
                    assertThat(teacherEntity.teacherId()).isNotBlank();
                    assertThat(teacherEntity.username()).isEqualTo("username");
                    assertThat(teacherEntity.lastname()).isEqualTo("Lastname");
                    assertThat(teacherEntity.firstname()).isEqualTo("Firstname");
                    assertThat(teacherEntity.resume()).isEqualTo("Core Qualifications : ...");
                });
    }

    @Test
    void should_patch_existing_teacher() throws Exception {
        // Given
        String username = "username";
        String teacherId = insertTeacher(username);
        // When
        ResultActions resultActions = mockMvc
                .perform(patch("/api/teachers/{teacherId}", teacherId)
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                            "lastname": "Shelby",
                                            "firstname": "Thomas",
                                            "resume": "Core Qualifications : ..."
                                        }
                                        """
                        )
                );
        // Then
        resultActions.andExpect(status().isNoContent());
        Optional<TeacherEntity> optTeacherEntity = findTeacherByUsername(username);
        assertThat(optTeacherEntity)
                .isPresent()
                .hasValueSatisfying(teacherEntity -> {
                    assertThat(teacherEntity.id()).isPositive();
                    assertThat(teacherEntity.teacherId()).isEqualTo(teacherId);
                    assertThat(teacherEntity.username()).isEqualTo(username);
                    assertThat(teacherEntity.lastname()).isEqualTo("Shelby");
                    assertThat(teacherEntity.firstname()).isEqualTo("Thomas");
                    assertThat(teacherEntity.resume()).isEqualTo("Core Qualifications : ...");
                });
    }

    @Test
    void should_delete_existing_teacher() throws Exception {
        // Given
        String username = "username";
        String teacherId = insertTeacher(username);
        // When
        ResultActions resultActions = mockMvc
                .perform(delete("/api/teachers/{teacherId}", teacherId)
                        .with(jwt()
                                .authorities(jwtGrantedAuthoritiesConverter)
                                .jwt(jwt -> jwt.claims(claims -> claims.put("roles", "admin"))))
                );
        // Then
        resultActions.andExpect(status().isNoContent());
        Optional<TeacherEntity> teacherEntity = findTeacherByUsername(username);
        assertThat(teacherEntity).isEmpty();
    }

    private String insertTeacher(String username) {
        String teacherId = UUID.randomUUID().toString();
        jdbcTemplate.execute("""
                INSERT INTO teachers (teacher_id, username, lastname, firstname, resume)
                VALUES ('%s', '%s', 'Lastname', 'Firstname', 'My resume')
                """.formatted(teacherId, username)
        );
        return teacherId;
    }

    private Optional<TeacherEntity> findTeacherByUsername(String username) {
        return jdbcTemplate.query(
                "SELECT * FROM teachers WHERE username = '%s'".formatted(username),
                new DataClassRowMapper<>(TeacherEntity.class)
        ).stream().findFirst();
    }
}