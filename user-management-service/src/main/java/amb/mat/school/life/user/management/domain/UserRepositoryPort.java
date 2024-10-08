package amb.mat.school.life.user.management.domain;

import jakarta.annotation.Nullable;

import java.util.Optional;

public interface UserRepositoryPort {

    /**
     * Indicate if an owner relationship exists between the specified user and owner
     *
     * @param username the {@link  Username} of the user
     * @param owner    the {@link  Username} of the owner
     * @return {@code true} if the relationship exists, {@code false} otherwise
     */
    boolean existsOwnerRelationshipBetween(Username username, Username owner);

    /**
     * Find the user identified specified username
     *
     * @param username the {@link  Username} of the user to find
     * @return the {@link User} found, empty otherwise
     */
    Optional<User> findByUserName(Username username);

    /**
     * Put the user into the repository and update its password
     *
     * @param user     the {@link User} to put
     * @param password the {@link Password} to update
     */
    void put(User user, @Nullable EncodedPassword password);

    /**
     * Remove the user identified specified username
     *
     * @param username the {@link  Username} of the user to remove
     */
    void remove(Username username);
}
