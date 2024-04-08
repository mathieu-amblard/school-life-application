package amb.mat.school.life.user.management.domain;

import amb.mat.school.life.user.management.domain.command.CreateUserAccountCommand;
import amb.mat.school.life.user.management.domain.query.FindUserAccountQuery;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;

import java.util.Optional;

public interface UserAccountService {

    /**
     * Get the {@link Username} of the connected user
     *
     * @return the {@link Username} of the connected user
     */
    Username getMyUsername();

    /**
     * Get the {@link UserAccount} of the connected user
     *
     * @return the {@link UserAccount} of the connected user
     */
    UserAccount getMyAccount();

    /**
     * Indicate if the user identified by username is owned by the specified owner
     *
     * @param username the username of the user
     * @param owner    the username of the owner
     * @return {@code true} if the user is owned by the owner, {@code false} otherwise
     */
    default boolean isOwnedBy(Username username, Username owner) {
        return isOwnedBy(new IsOwnedByQuery(username, owner));
    }

    /**
     * Indicate if the user identified by username is owned by the specified owner
     *
     * @param query the {@link IsOwnedByQuery} to use
     * @return {@code true} if the user is owned by the owner, {@code false} otherwise
     */
    boolean isOwnedBy(IsOwnedByQuery query);

    /**
     * Find a user account identified by the specified username
     *
     * @param query the {@link FindUserAccountQuery} to use
     * @return the user account found, empty otherwise
     */
    Optional<UserAccount> find(FindUserAccountQuery query);

    /**
     * Create a new user account
     *
     * @param command the {@link CreateUserAccountCommand} to use
     * @return the {@link UserAccount} created
     */
    UserAccount createAccount(CreateUserAccountCommand command);
}
