package amb.mat.school.life.user.management.persistence;

import amb.mat.school.life.user.management.domain.EncodedPassword;
import amb.mat.school.life.user.management.domain.Password;
import amb.mat.school.life.user.management.domain.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class PasswordEncoderAdapter implements PasswordEncoder {

    private static final String BRCYPT_FORMAT = "{bcrypt}%s";
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
    private static final BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public EncodedPassword encode(Password password) {
        String encoded = password.value();
        if (!BCRYPT_PATTERN.matcher(encoded).matches()) {
            encoded = BRCYPT_FORMAT.formatted(BCRYPT_PASSWORD_ENCODER.encode(encoded));
        }
        return new EncodedPassword(encoded);
    }
}
