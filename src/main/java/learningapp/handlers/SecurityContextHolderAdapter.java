package learningapp.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

import static java.util.Optional.ofNullable;

@Slf4j
public class SecurityContextHolderAdapter {

    public static String getCurrentUser() throws AuthenticationException {
        log.info("get current logged in user...");
        return ofNullable((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .map(UserDetails::getUsername)
                .orElseThrow(() -> new AuthenticationException("Authentication not found") {
                });
    }

}
