package amb.mat.school.life.user.management.domain;

import java.util.List;

public enum Role {
    ADMIN {
        @Override
        public boolean isCompatibleWith(List<Role> roles) {
            return !roles.contains(STUDENT);
        }
    },
    TEACHER,
    STUDENT;

    public boolean isCompatibleWith(List<Role> roles) {
        return true;
    }
}
