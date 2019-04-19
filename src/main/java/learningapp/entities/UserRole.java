package learningapp.entities;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    PROFESSOR,
    STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
