package learningapp.factory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import learningapp.dtos.AuthenticationDto;
import learningapp.entities.User;

import static learningapp.utils.TestConstants.USER_EMAIL;
import static learningapp.utils.TestConstants.USER_NAME;
import static learningapp.utils.TestConstants.USER_PASSWORD;
import static learningapp.utils.TestConstants.USER_ROLE;

public class UserFactory {

    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static AuthenticationDto.AuthenticationDtoBuilder generateAuthenticationDtoBuilder() {
        return AuthenticationDto.builder();
    }

    public static User.UserBuilder generateUserBuilder() {
        return User.builder()
                .username(USER_NAME)
                .password(encoder.encode(USER_PASSWORD))
                .email(USER_EMAIL)
                .userRole(USER_ROLE);
    }

    public static User generateUser() {
        return generateUserBuilder().build();
    }

}
