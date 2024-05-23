package amb.mat.school.life.teacherservice.client;

import java.util.Set;

public record UserDto(
        String emailAddress,
        String password, // TODO -> should never be set when read
        Set<String> roles
) {

    public static final String TEACHER_ROLE = "TEACHER";

    public UserDto(String emailAddress, String password) {
        this(emailAddress, password, Set.of(TEACHER_ROLE));
    }
}
