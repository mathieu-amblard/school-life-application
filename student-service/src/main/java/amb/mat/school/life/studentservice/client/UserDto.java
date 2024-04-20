package amb.mat.school.life.studentservice.client;

import java.util.Set;

public record UserDto(
        String password,
        String emailAddress,
        Set<String> roles
) {

    public static final String STUDENT_ROLE = "STUDENT";

    public UserDto(String password, String emailAddress) {
        this(password, emailAddress, Set.of(STUDENT_ROLE));
    }
}
