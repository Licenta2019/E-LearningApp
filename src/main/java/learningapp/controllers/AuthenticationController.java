package learningapp.controllers;

import learningapp.apis.AuthenticationApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;
import learningapp.services.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "authentication")
@RequestMapping(path = "/")
@CrossOrigin
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping(value = "login")
    public UserDto login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return authenticationService.login(authenticationDto);
    }

}
