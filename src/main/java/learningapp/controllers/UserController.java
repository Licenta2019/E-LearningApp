package learningapp.controllers;

import learningapp.apis.AuthenticationApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;
import learningapp.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
