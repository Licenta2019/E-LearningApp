package learningapp.services.impl;

import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;
import learningapp.handlers.LearningappPasswordEncoder;
import learningapp.repositories.UserRepository;
import learningapp.security.JwtTokenProvider;
import learningapp.services.UserService;
import lombok.extern.slf4j.Slf4j;

import static learningapp.exceptions.ExceptionMessages.USER_INVALID_PASSWORD;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.mappers.UserMapper.toBaseUserDto;
import static learningapp.mappers.UserMapper.toUser;
import static learningapp.mappers.UserMapper.toUserDto;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseUserDto login(AuthenticationDto authenticationDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));

        User user = userRepository.findByUsername(authenticationDto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        String token = jwtTokenProvider.createToken(authenticationDto.getUsername(), Collections.singletonList(user.getUserRole()));

        return toBaseUserDto(user, token);
    }

    private User getUserEntity(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void updateUser(UUID id, UserDto userDto) {
        log.info("update user" + userDto.getUsername());
        User user = getUserEntity(id);

        if (!LearningappPasswordEncoder.getInstance().matches(userDto.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException(USER_INVALID_PASSWORD);
        }

        toUser(user, userDto);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(UUID id) {
        User user = getUserEntity(id);

        return toUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkPassword(UUID id, String password) {
        User user = getUserEntity(id);

        if (!LearningappPasswordEncoder.getInstance().matches(password, user.getPassword())) {
            throw new BadCredentialsException(USER_INVALID_PASSWORD);
        }
    }
}
