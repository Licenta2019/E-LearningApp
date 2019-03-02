package learningapp.factory;

import learningapp.dtos.AuthenticationDto;
import learningapp.entities.User;

import static learningapp.utils.TestConstants.USER_EMAIL;
import static learningapp.utils.TestConstants.USER_NAME;
import static learningapp.utils.TestConstants.USER_PASSWORD;
import static learningapp.utils.TestConstants.USER_ROLE;

public class UserFactory {

    public static AuthenticationDto.AuthenticationDtoBuilder generateAuthenticationDtoBuilder() {
        return AuthenticationDto.builder();
    }

    public static User generateUser() {
        return User.builder()
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .email(USER_EMAIL)
                .userRole(USER_ROLE)
                .build();
    }

}
