package amb.mat.school.life.user.management.domain.command;

import amb.mat.school.life.user.management.domain.EmailAddress;
import amb.mat.school.life.user.management.domain.Password;
import amb.mat.school.life.user.management.domain.Role;
import amb.mat.school.life.user.management.domain.Username;

import java.util.Set;

/**
 * Command used to create a new user
 *
 * @param username
 * @param password
 * @param emailAddress
 * @param roles
 * @param owner
 */
public record CreateUserCommand(
        Username username,
        Password password,
        EmailAddress emailAddress,
        Set<Role> roles,
        Username owner
) {

    private static final String ERROR_MESSAGE_TEMPLATE = "to create a new user, %s";

    public CreateUserCommand {
        checkUsernameMandatory(username);
        checkPasswordMandatory(password);
        checkEmailAddressMandatory(emailAddress);
        checkRolesMandatory(roles);
        checkRolesNotContainAdmin(roles);
        checkOwnerMandatory(owner);
    }

    private void checkUsernameMandatory(Username username) {
        if (username == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the username is required"));
        }
    }

    private void checkPasswordMandatory(Password password) {
        if (password == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the password is required"));
        }
    }

    private void checkEmailAddressMandatory(EmailAddress emailAddress) {
        if (emailAddress == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the email address is required"));
        }
    }

    private void checkRolesMandatory(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the roles of the new user is/are required"));
        }
    }

    private void checkRolesNotContainAdmin(Set<Role> roles) {
        if (roles != null && roles.contains(Role.ADMIN)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the role of the new user must not be %s".formatted(Role.ADMIN.name())));
        }
    }

    private void checkOwnerMandatory(Username owner) {
        if (owner == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_TEMPLATE.formatted("the owner is required"));
        }
    }
}
