package amb.mat.school.life.user.management.domain;

public interface PasswordEncoder {

    EncodedPassword encode(Password password);
}
