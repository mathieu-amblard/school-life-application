package amb.mat.school.life.student.domain.student.command;

import amb.mat.school.life.student.domain.student.Birthdate;
import amb.mat.school.life.student.domain.student.Firstname;
import amb.mat.school.life.student.domain.student.Lastname;
import amb.mat.school.life.student.domain.student.StudentId;
import amb.mat.school.life.student.domain.user.Username;
import amb.mat.school.life.student.domain.Identifier;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Optional;

// TODO -> Ideally builder for commands...
public record UpdateStudentCommand(
        Identifier identifier,
        @Nullable Lastname lastname,
        @Nullable Firstname firstname,
        @Nullable Birthdate birthdate
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to update a student, %s";

    public UpdateStudentCommand(String identifier, String lastname, String firstname, LocalDate birthdate) {
        this(
                getIdentifier(identifier),
                Optional.ofNullable(lastname).map(Lastname::new).orElse(null),
                Optional.ofNullable(firstname).map(Firstname::new).orElse(null),
                Optional.ofNullable(birthdate).map(Birthdate::new).orElse(null)
        );
    }

    public UpdateStudentCommand {
        checkIdentifierMandatory(identifier);
    }

    private static Identifier getIdentifier(String identifier) {
        Identifier temp;
        try {
            temp = new StudentId(identifier);
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