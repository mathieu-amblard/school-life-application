package amb.mat.school.life.user.management.domain;

import amb.mat.school.life.user.management.domain.command.CreateUserCommand;
import amb.mat.school.life.user.management.domain.command.UpdateUserCommand;
import amb.mat.school.life.user.management.domain.query.FindUserQuery;
import amb.mat.school.life.user.management.domain.query.IsOwnedByQuery;

import java.util.Optional;

public interface UserService {

    /**
     * Get the {@link Username} of the connected user
     *
     * @return the {@link Username} of the connected user
     */
    Username getMyUsername();

    /**
     * Get the {@link User} of the connected user
     *
     * @return the {@link User} of the connected user
     */
    User getMyUser();

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
     * Find a user identified by the specified username
     *
     * @param query the {@link FindUserQuery} to use
     * @return the user found, empty otherwise
     */
    Optional<User> find(FindUserQuery query);

    /**
     * Create a new user
     *
     * @param command the {@link CreateUserCommand} to use
     * @return the {@link User} created
     */
    User createUser(CreateUserCommand command);

    /**
     * Update an existing user
     *
     * @param command the {@link UpdateUserCommand} to use
     * @return the {@link User} updated
     */
    User updateUser(UpdateUserCommand command);
}
