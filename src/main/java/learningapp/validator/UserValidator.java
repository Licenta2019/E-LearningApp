package learningapp.validator;

import org.springframework.stereotype.Component;

import learningapp.dtos.user.UserDto;
import learningapp.exceptions.base.DuplicateEntityException;
import learningapp.repositories.UserRepository;

import static learningapp.exceptions.ExceptionMessages.DUPLICATE_USER;

@Component
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUser(UserDto userDto) {

        userRepository.findByUsername(userDto.getUsername()).ifPresent((user) -> {
            throw new DuplicateEntityException(DUPLICATE_USER + user.getUsername());
        });
    }

}
