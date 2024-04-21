package amb.mat.school.life.studentservice.client;

import java.util.Set;

public record UserDto(
        String emailAddress,
        String password, // TODO -> should never be set when read
        Set<String> roles
) {

    public static final String STUDENT_ROLE = "STUDENT";

    public UserDto(String emailAddress, String password) {
        this(password, emailAddress, Set.of(STUDENT_ROLE));
    }
}
