package learningapp.services.impl;

import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.UserDto;
import learningapp.entities.User;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.UserRepository;
import learningapp.security.JwtTokenProvider;
import learningapp.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.mappers.UserMapper.toUserDto;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto login(AuthenticationDto authenticationDto) {
        User user = userRepository.findByUsername(authenticationDto.getUsername())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),
                authenticationDto.getPassword()));

        return toUserDto(user, jwtTokenProvider.createToken(user.getUsername(), user.getUserRole()));
    }
}
