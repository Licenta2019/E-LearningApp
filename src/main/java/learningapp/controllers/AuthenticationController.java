package learningapp.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.AuthenticationApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.services.AuthenticationService;

@RestController(value = "authentication")
@RequestMapping(path = "/login")
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping
    public void login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        authenticationService.login(authenticationDto);
    }

}
