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
    boolean existsOwnerRelationship(Username username, Username owner);

    /**
     * Find the user account of the specified username
     *
     * @param username the username of the user
     * @return the {@link UserAccount} found, empty otherwise
     */
    Optional<UserAccount> findByUserName(Username username);
}
