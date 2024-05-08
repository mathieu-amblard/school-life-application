package amb.mat.school.life.studentservice.domain.student.command;

import amb.mat.school.life.studentservice.domain.Identifier;
import amb.mat.school.life.studentservice.domain.student.StudentId;
import amb.mat.school.life.studentservice.domain.user.Username;

public record DeleteStudentCommand(
        Identifier identifier
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to delete a student, %s";

    public DeleteStudentCommand(String identifier) {
        this(getIdentifier(identifier));
    }

    public DeleteStudentCommand {
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
