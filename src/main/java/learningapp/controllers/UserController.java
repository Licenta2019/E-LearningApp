package learningapp.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.AuthenticationApi;
import learningapp.apis.UserApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.services.UserService;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController(value = "UserController")
@RequestMapping(path = "/user")
@CrossOrigin
public class UserController implements AuthenticationApi, UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping(value = "/login")
    public BaseUserDto login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        return userService.login(authenticationDto);
    }

    @Override
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable @Valid String id) {
        return userService.getUser(uuidFromString(id));
    }

    @Override
    @PutMapping("/{id}")
    public void updateUser(@PathVariable @Valid String id, @RequestBody @Valid UserDto userDto) {
        userService.updateUser(uuidFromString(id), userDto);
    }
}
