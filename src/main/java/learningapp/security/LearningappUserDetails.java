package learningapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;
import learningapp.repositories.UserRepository;

/**
 * Implementation for spring details service.
 */
@Service
public class LearningappUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    public LearningappUserDetails(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getUserRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
