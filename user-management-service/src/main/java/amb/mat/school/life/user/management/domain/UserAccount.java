package amb.mat.school.life.user.management.domain;

import java.util.List;

// TODO record ?

/**
 * Business Rules :
 * - The username is mandatory.
 * - The email address is mandatory.
 * - The account must have at least one role.
 * - ADMIN role is not compatible with STUDENT.
 */
public class UserAccount {

    private final Username username;
    private final EmailAddress emailAddress;
    private final List<Role> roles;
    private final Username owner;

    public UserAccount(Username username, EmailAddress emailAddress, List<Role> roles, Username owner) {
        checkUsernameMandatory(username);
        checkEmailAddressMandatory(emailAddress);
        checkRoleCompatibility(roles);
        this.username = username;
        this.emailAddress = emailAddress;
        this.roles = roles;
        this.owner = owner;
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException("username must not be null");
        }
    }

    private void checkEmailAddressMandatory(EmailAddress emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException("emailAddress must not be null");
        }
    }

    private void checkRoleCompatibility(List<Role> roles) {
        if (roles.stream().anyMatch(role -> !role.isCompatibleWith(roles))) {
            throw new IllegalArgumentException("%s are incompatible".formatted(roles));
        }
    }
}
