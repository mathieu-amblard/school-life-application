package amb.mat.school.life.user.management.domain.query;

import amb.mat.school.life.user.management.domain.Username;

/**
 * One of the main advantage of using command/query is that you can validate parameters directly in the object itself vs validating
 * the parameters within the method of the service
 * <p>
 * Query used to validate if the user identified by the username is owned by the owner
 *
 * @param username the username of the user
 * @param owner    the username of the owner
 */
public record IsOwnedByQuery(Username username, Username owner) {

    public IsOwnedByQuery {
        checkUsernameMandatory(username);
        checkOwnerMandatory(owner);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
    }

    private void checkOwnerMandatory(Username owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner must not be null");
        }
    }
}
