package learningapp.dtos.user;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import learningapp.entities.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseUserDto {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Builder
    public UserDto(@NotNull UUID id, @NotNull UserRole userRole, @NotNull String jwtToken, @NotNull String username, @NotNull String email,
                   @NotNull boolean notificationsEnabled, @NotNull String password) {
        super(id, userRole, jwtToken, notificationsEnabled);
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
