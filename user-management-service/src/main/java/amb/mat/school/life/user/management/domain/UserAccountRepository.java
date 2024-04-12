package amb.mat.school.life.user.management.domain;

import java.util.Optional;

public interface UserAccountRepository {

    /**
     * Indicate if an owner relationship exists between the specified user and owner
     *
     * @param username the username of the user
     * @param owner    the username of the owner
     * @return {@code true} if the relationship exists, {@code false} otherwise
     */
    boolean existsOwnerRelationshipBetween(Username username, Username owner);

    /**
     * Find the user account of the specified username
     *
     * @param username the username of the user
     * @return the {@link UserAccount} found, empty otherwise
     */
    Optional<UserAccount> findByUserName(Username username);

    /**
     * Put the user account into the repository
     *
     * @param userAccount the {@link UserAccount} to put
     * @return the {@link UserAccount} put
     */
    UserAccount put(UserAccount userAccount);

    /**
     * Put the user account into the repository and update its password
     *
     * @param userAccount the {@link UserAccount} to put
     * @param password    the {@link Password} to update
     * @return the {@link UserAccount} put
     */
    UserAccount put(UserAccount userAccount, EncodedPassword password);
}
