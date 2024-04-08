package amb.mat.school.life.user.management.domain;

import java.util.Collection;

public enum Role {
    ADMIN {
        @Override
        public boolean isCompatibleWith(Collection<Role> roles) {
            return !roles.contains(STUDENT);
        }
    },
    TEACHER,
    STUDENT;

    public boolean isCompatibleWith(Collection<Role> roles) {
        return true;
    }

    public static Role of(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
