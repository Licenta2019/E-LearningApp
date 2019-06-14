package learningapp.dtos.user;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import learningapp.entities.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseUserDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String oldPassword;

    @NotNull
    private String email;

    @Builder
    public UserDto(UUID id, UserRole userRole, String jwtToken, String username, String email,
                   boolean notificationsEnabled, String password, String oldPassword) {
        super(id, userRole, jwtToken, notificationsEnabled);
        this.username = username;
        this.email = email;
        this.password = password;
        this.oldPassword = oldPassword;
    }

}
