package amb.mat.school.life.user.management.domain.query;

import amb.mat.school.life.user.management.domain.Username;

/**
 * One of the main advantage of using command/query is that you can validate parameters directly in the object itself vs validating
 * the parameters within the method of the service.
 * <p>
 * Query used to verify if the user identified by the username is owned by the owner
 *
 * @param username the username of the user
 * @param owner    the username of the owner
 */
public record IsOwnedByQuery(Username username, Username owner) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to verify if the user is owned a specific user, %s";

    public IsOwnedByQuery {
        checkUsernameMandatory(username);
        checkOwnerMandatory(owner);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username of the user is required"));
        }
    }

    private void checkOwnerMandatory(Username owner) {
        if (owner == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username of the owner is required"));
        }
    }
}
