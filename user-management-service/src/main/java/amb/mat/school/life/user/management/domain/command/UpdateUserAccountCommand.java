package amb.mat.school.life.user.management.domain.command;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Password;
import amb.mat.school.life.user.management.domain.Username;

// TODO

/**
 * Command used to update an existing user account
 *
 * @param username
 * @param password
 * @param emailAddress
 */
public record UpdateUserAccountCommand(
        Username username,
        Password password,
        EmailAddress emailAddress
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to update a new user account, %s";

    public UpdateUserAccountCommand {
        checkUsernameMandatory(username);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
        }
    }
}
