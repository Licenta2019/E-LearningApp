package learningapp.services;

import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.UserFactory.generateAuthenticationDtoBuilder;
import static learningapp.factory.UserFactory.generateUserDto;
import static learningapp.factory.UserFactory.generateUserDtoBuilder;
import static learningapp.utils.TestConstants.INEXISTENT_PASSWORD;
import static learningapp.utils.TestConstants.INEXISTENT_USER;
import static learningapp.utils.TestConstants.USER_NAME;
import static learningapp.utils.TestConstants.USER_PASSWORD;
import static learningapp.utils.TestConstants.USER_UPDATED_EMAIL;
import static learningapp.utils.TestConstants.USER_UPDATED_NAME;
import static learningapp.utils.TestConstants.USER_UPDATED_PASSWORD;
import static learningapp.utils.TestConstants.USER_UPDATED_ROLE;

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
        BaseUserDto baseUserDto = userService.login(authenticationDto);

        //then
        assertNotNull(baseUserDto);
        assertEquals(user.getUserRole(), baseUserDto.getUserRole());
        assertEquals(user.getId(), baseUserDto.getId());
        assertEquals(user.isNotificationEnabled(), baseUserDto.isNotificationsEnabled());
        assertNotNull(baseUserDto.getJwtToken());
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

    @Test
    public void givenInexistentUser_whenGetUser_thenExceptionIsThrown() {

        //when
        assertThatThrownByError(() -> userService.getUser(UUID.randomUUID()),
                //then

                NotFoundException.class,
                USER_NOT_FOUND);
    }

    @Test
    public void givenExistentUser_whenGetUser_thenUserDtoReturned() {

        //given
        User user = findOrCreateUser(USER_NAME);

        //when
        UserDto userDto = userService.getUser(user.getId());

        //then
        assertNotNull(userDto);
        assertUsersEquals(user, userDto);
    }

    @Test
    public void givenInexistentUser_whenUpdateUser_thenExceptionIsThrown() {

        //when
        assertThatThrownByError(() -> userService.updateUser(UUID.randomUUID(), generateUserDto()),
                //then

                NotFoundException.class,
                USER_NOT_FOUND);
    }

    @Test
    public void givenValidUserDto_whenUpdateUser_thenUserUpdated() {

        //given
        User user = findOrCreateUser(USER_NAME);

        UserDto userDto = generateUserDtoBuilder()
                .id(user.getId())
                .userRole(USER_UPDATED_ROLE)
                .notificationsEnabled(false)
                .email(USER_UPDATED_EMAIL)
                .password(USER_UPDATED_PASSWORD)
                .username(USER_UPDATED_NAME)
                .build();

        //when
        userService.updateUser(user.getId(), userDto);

        //then
        User updatedUser = userRepository.findById(user.getId()).get();

        assertUsersEquals(updatedUser, userDto);
    }

    private void assertUsersEquals(User user, UserDto userDto) {
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getEmail(), userDto.getEmail());
//        assertEquals(user.getUserRole(), userDto.getUserRole());
        assertEquals(user.isNotificationEnabled(), userDto.isNotificationsEnabled());
    }


}
