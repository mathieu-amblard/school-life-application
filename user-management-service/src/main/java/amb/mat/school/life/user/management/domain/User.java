package amb.mat.school.life.user.management.domain;

import java.util.Set;

// TODO record ?

/**
 * Business Rules :
 * - The username is mandatory.
 * - The email address is mandatory.
 * - The user must have at least one role.
 * - ADMIN role is not compatible with STUDENT.
 */
public class User {

    private final Username username;
    private EmailAddress emailAddress;
    private final Set<Role> roles;
    private final Username owner;

    public User(Username username, EmailAddress emailAddress, Set<Role> roles, Username owner) {
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

    private void checkRoleCompatibility(Set<Role> roles) {
        if (roles.stream().anyMatch(role -> !role.isCompatibleWith(roles))) {
            throw new IllegalArgumentException("%s are incompatible".formatted(roles));
        }
    }

    public Username username() {
        return username;
    }

    public EmailAddress emailAddress() {
        return emailAddress;
    }

    public void updateEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Role> roles() {
        return roles;
    }

    public Username owner() {
        return owner;
    }
}
