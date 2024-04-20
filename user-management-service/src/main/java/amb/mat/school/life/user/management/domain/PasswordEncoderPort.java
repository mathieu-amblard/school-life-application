package amb.mat.school.life.user.management.domain;

public interface PasswordEncoderPort {

    EncodedPassword encode(Password password);
}
