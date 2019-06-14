package learningapp.handlers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LearningappPasswordEncoder {

    private static PasswordEncoder instance = new BCryptPasswordEncoder();

    public static PasswordEncoder getInstance() {
        return instance;
    }

}
