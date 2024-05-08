package amb.mat.school.life.user.management.domain.command;

import amb.mat.school.life.user.management.domain.Username;

/**
 * Command used to delete an existing user
 *
 * @param username
 */
public record DeleteUserCommand(Username username) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to delete a user, %s";

    public DeleteUserCommand {
        checkUsernameMandatory(username);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
        }
    }
}
