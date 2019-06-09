package learningapp.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import learningapp.dtos.AuthenticationDto;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.UserFactory.generateAuthenticationDtoBuilder;
import static learningapp.utils.TestConstants.INEXISTENT_PASSWORD;
import static learningapp.utils.TestConstants.INEXISTENT_USER;
import static learningapp.utils.TestConstants.USER_NAME;
import static learningapp.utils.TestConstants.USER_PASSWORD;

public class UserIT extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void givenValidAuthDto_whenLogin_thenOk() {

        //given
        User user = findOrCreateUser(USER_NAME);

        AuthenticationDto authenticationDto = generateAuthenticationDtoBuilder()
                .username(user.getUsername())
                .password(USER_PASSWORD)
                .build();

        //when
        assertThatCode(() -> userService.login(authenticationDto))

                //then
                .doesNotThrowAnyException();
    }

    @Test
    public void givenInexistentUsername_whenLogin_thenExceptionIsThrown() {

        //given
        AuthenticationDto authenticationDto = generateAuthenticationDtoBuilder()
                .username(INEXISTENT_USER)
                .password(INEXISTENT_PASSWORD)
                .build();

        //when
        assertThatThrownByError(() -> userService.login(authenticationDto),

                //then
                InternalAuthenticationServiceException.class, USER_NOT_FOUND);
    }

}
