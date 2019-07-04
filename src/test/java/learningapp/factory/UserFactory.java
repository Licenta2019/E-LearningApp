package learningapp.factory;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.UserDto;
import learningapp.entities.User;
import learningapp.entities.UserRole;
import learningapp.handlers.LearningappPasswordEncoder;

import static learningapp.utils.TestConstants.USER_EMAIL;
import static learningapp.utils.TestConstants.USER_NAME;
import static learningapp.utils.TestConstants.USER_PASSWORD;
import static learningapp.utils.TestConstants.USER_ROLE;

public class UserFactory {

    private static PasswordEncoder encoder = LearningappPasswordEncoder.getInstance();

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

    public static UserDto.UserDtoBuilder generateUserDtoBuilder() {
        return UserDto.builder()
                .id(UUID.randomUUID())
                .username(USER_NAME)
                .password(encoder.encode(USER_PASSWORD))
                .email(USER_EMAIL)
                .userRole(UserRole.STUDENT)
                .notificationsEnabled(false);
    }

    public static UserDto generateUserDto() {
        return generateUserDtoBuilder().build();
    }

}
