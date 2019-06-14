package learningapp.services;

import java.util.UUID;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;

public interface UserService {

    /**
     * Perform login for user described by the authentication dto.
     *
     * @param authenticationDto
     * @return
     */
    BaseUserDto login(AuthenticationDto authenticationDto);

    /**
     * Update a specific user via it's id.
     *
     * @param uuidFromString
     * @param userDto
     */
    void updateUser(UUID uuidFromString, UserDto userDto);

    /**
     * Retrieve a specific user.
     *
     * @param uuidFromString
     * @return
     */
    UserDto getUser(UUID uuidFromString);

    /**
     * Check if the given password matches the password of the current logged-in user.
     *
     * @param id
     * @param password
     */
    void checkPassword(UUID id, String password);

}
