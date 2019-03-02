package learningapp.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import learningapp.dtos.AuthenticationDto;
import learningapp.entities.User;
import learningapp.exceptions.NotFoundException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.UserFactory.generateAuthenticationDtoBuilder;
import static learningapp.utils.TestConstants.INEXISTENT_PASSWORD;
import static learningapp.utils.TestConstants.INEXISTENT_USER;

public class AuthenticationIT extends BaseIntegrationTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void givenValidAuthDto_whenLogin_thenOk() {

        //given
        User user = createRandomUser();

        AuthenticationDto authenticationDto = generateAuthenticationDtoBuilder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();

        //when
        assertThatCode(() -> authenticationService.login(authenticationDto))

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
        assertThatThrownByError(() -> authenticationService.login(authenticationDto),

                //then
                NotFoundException.class, USER_NOT_FOUND);
    }

}
