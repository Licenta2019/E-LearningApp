package learningapp.dtos.user;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import learningapp.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDto {

    @NotNull
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //token
    @NotNull
    private String jwtToken;

    @NotNull
    private boolean notificationsEnabled;

}
