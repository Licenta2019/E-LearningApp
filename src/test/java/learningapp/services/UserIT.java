package learningapp.services;

import java.util.Optional;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.entities.User;
import learningapp.exceptions.base.DuplicateEntityException;
import learningapp.exceptions.base.NotFoundException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static learningapp.exceptions.ExceptionMessages.DUPLICATE_USER;
import static learningapp.exceptions.ExceptionMessages.USER_INVALID_PASSWORD;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.UserFactory.generateAuthenticationDtoBuilder;
import static learningapp.factory.UserFactory.generateUserDto;
import static learningapp.factory.UserFactory.generateUserDtoBuilder;
import static learningapp.utils.TestConstants.INEXISTENT_PASSWORD;
import static learningapp.utils.TestConstants.INEXISTENT_USER;
import static learningapp.utils.TestConstants.INVALID_PASSWORD;
import static learningapp.utils.TestConstants.NEW_USER_NAME;
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
        assertEquals(user.getId(), userDto.getId());
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
    @Ignore
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
        assertEquals(user.getId(), userDto.getId());
    }

    @Test
    public void givenCorrectPassword_whenCheckPassword_thenOk() {

        //given
        User user = findOrCreateUser(USER_NAME);

        //when
        assertThatCode(() -> userService.checkPassword(user.getId(), USER_PASSWORD))

                //then
                .doesNotThrowAnyException();
    }

    @Test
    public void givenInvalidPassword_whenCheckPassword_thenExceptionisThrown() {

        //given
        UUID id = findOrCreateUser(USER_NAME).getId();

        //when
        assertThatThrownByError(() -> userService.checkPassword(id, INVALID_PASSWORD),

                //then
                BadCredentialsException.class,
                USER_INVALID_PASSWORD);
    }

    @Test
    public void givenValidUserDto_whenRegisterUser_thenOk() {

        UserDto userDto = generateUserDtoBuilder()
                .username(NEW_USER_NAME)
                .build();

        userService.saveUser(userDto);

        Optional<User> user = userRepository.findByUsername(NEW_USER_NAME);

        assertTrue(user.isPresent());

        assertUsersEquals(user.get(), userDto);
    }

    @Test
    public void givenDuplicateUser_whenRegisterUser_thenOk() {

        User user = findOrCreateUser(USER_NAME);

        UserDto userDto = generateUserDtoBuilder()
                .username(USER_NAME)
                .build();

        assertThatThrownByError(() -> userService.saveUser(userDto),
                DuplicateEntityException.class,
                DUPLICATE_USER + USER_NAME);

    }

    private void assertUsersEquals(User user, UserDto userDto) {
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.isNotificationEnabled(), userDto.isNotificationsEnabled());
    }

}
