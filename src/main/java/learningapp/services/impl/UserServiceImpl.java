package learningapp.services.impl;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;
import learningapp.entities.User;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.UserRepository;
import learningapp.security.JwtTokenProvider;
import learningapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

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
    public UserDto login(AuthenticationDto authenticationDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));

        User user = userRepository.findByUsername(authenticationDto.getUsername())
                .orElseThrow(() -> new NotFoundException("bfb"));

        String token = jwtTokenProvider.createToken(authenticationDto.getUsername(), Collections.singletonList(user.getUserRole()));

        return toUserDto(user, token);
    }
}
