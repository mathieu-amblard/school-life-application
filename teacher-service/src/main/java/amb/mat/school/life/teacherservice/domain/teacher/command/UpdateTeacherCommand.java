package amb.mat.school.life.teacherservice.domain.teacher.command;

import amb.mat.school.life.teacherservice.domain.Identifier;
import amb.mat.school.life.teacherservice.domain.teacher.Birthdate;
import amb.mat.school.life.teacherservice.domain.teacher.Firstname;
import amb.mat.school.life.teacherservice.domain.teacher.Lastname;
import amb.mat.school.life.teacherservice.domain.teacher.TeacherId;
import amb.mat.school.life.teacherservice.domain.user.Username;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Optional;

// TODO -> Ideally builder for commands...
public record UpdateTeacherCommand(
        Identifier identifier,
        @Nullable Lastname lastname,
        @Nullable Firstname firstname,
        @Nullable Birthdate birthdate
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to update a teacher, %s";

    public UpdateTeacherCommand(String identifier, String lastname, String firstname, LocalDate birthdate) {
        this(
                getIdentifier(identifier),
                Optional.ofNullable(lastname).map(Lastname::new).orElse(null),
                Optional.ofNullable(firstname).map(Firstname::new).orElse(null),
                Optional.ofNullable(birthdate).map(Birthdate::new).orElse(null)
        );
    }

    public UpdateTeacherCommand {
        checkIdentifierMandatory(identifier);
    }

    private static Identifier getIdentifier(String identifier) {
        Identifier temp;
        try {
            temp = new TeacherId(identifier);
        } catch (IllegalArgumentException e) {
            temp = new Username(identifier);
        }
        return temp;
    }

    private void checkIdentifierMandatory(Identifier identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the identifier is required"));
        }
    }
}