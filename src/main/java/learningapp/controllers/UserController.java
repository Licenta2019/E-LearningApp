package learningapp.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.AuthenticationApi;
import learningapp.apis.UserApi;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.services.UserService;
import lombok.extern.slf4j.Slf4j;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController(value = "UserController")
@RequestMapping(path = "/user")
@CrossOrigin
@Slf4j
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
    @PostMapping("/{id}/password")
    public void validatePassword(@PathVariable String id, @NotNull String password) {
        userService.checkPassword(uuidFromString(id), password);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        log.info("update user in controller" + userDto.getUsername());
        userService.updateUser(uuidFromString(id), userDto);
    }
}
