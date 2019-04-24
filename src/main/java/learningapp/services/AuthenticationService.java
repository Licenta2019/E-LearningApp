package learningapp.services;

import learningapp.dtos.AuthenticationDto;

public interface AuthenticationService {

    /**
     * Perform login for user described by the authentication dto.
     *
     * @param authenticationDto
     */
    void login(AuthenticationDto authenticationDto);

}
