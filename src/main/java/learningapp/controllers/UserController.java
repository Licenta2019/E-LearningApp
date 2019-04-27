package learningapp.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.AuthenticationApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;
import learningapp.services.UserService;

@RestController(value = "user")
@RequestMapping(path = "/")
@CrossOrigin
public class UserController implements AuthenticationApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping(value = "login")
    public UserDto login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }

}
