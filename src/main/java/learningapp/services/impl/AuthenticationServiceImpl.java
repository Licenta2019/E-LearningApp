package learningapp.services.impl;

import learningapp.dtos.AuthenticationDto;
import learningapp.entities.User;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.UserRepository;
import learningapp.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void login(AuthenticationDto authenticationDto) {
        User user = userRepository.findByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        log.info("Succesufully logged-in:" + user.getUsername());
    }

}
