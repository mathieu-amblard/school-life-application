package amb.mat.school.life.user.management.domain.command;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Password;
import amb.mat.school.life.user.management.domain.Username;
import org.springframework.lang.Nullable;

/**
 * Command used to update an existing user
 *
 * @param username
 * @param password
 * @param emailAddress
 */
public record UpdateUserCommand(
        Username username,
        @Nullable
        Password password,
        @Nullable
        EmailAddress emailAddress
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to update a user, %s";

    public UpdateUserCommand {
        checkUsernameMandatory(username);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
        }
    }

    public boolean requiresUpdate(EmailAddress currentEmailAddress) {
        return password != null || (emailAddress != null && !emailAddress.equals(currentEmailAddress));
    }
}
