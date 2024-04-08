package amb.mat.school.life.user.management.domain.query;

import amb.mat.school.life.user.management.domain.Username;

/**
 * Query used to find a user account
 *
 * @param username the username of the user account to find
 */
public record FindUserAccountQuery(Username username) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to find a user account, %s";

    public FindUserAccountQuery {
        checkUsernameMandatory(username);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username of the user is required"));
        }
    }
}
