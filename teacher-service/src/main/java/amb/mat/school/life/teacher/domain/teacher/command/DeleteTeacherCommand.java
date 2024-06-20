package amb.mat.school.life.teacher.domain.teacher.command;

import amb.mat.school.life.teacher.domain.Identifier;
import amb.mat.school.life.teacher.domain.user.Username;
import amb.mat.school.life.teacher.domain.teacher.TeacherId;

public record DeleteTeacherCommand(
        Identifier identifier
) implements Command {

    private static final String ERROR_MESSAGE_TEMPLATE = "to delete a teacher, %s";

    public DeleteTeacherCommand(String identifier) {
        this(getIdentifier(identifier));
    }

    public DeleteTeacherCommand {
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
