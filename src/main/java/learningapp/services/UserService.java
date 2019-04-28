package learningapp.services;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;

public interface UserService {

    /**
     * Perform login for user described by the authentication dto.
     *
     * @param authenticationDto
     */
    UserDto login(AuthenticationDto authenticationDto);

}
