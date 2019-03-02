package learningapp.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.AuthenticationDto;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.UserRepository;
import learningapp.services.AuthenticationService;

import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void login(AuthenticationDto authenticationDto) {
        userRepository.findByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

}
