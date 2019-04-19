package learningapp.dtos;

import learningapp.entities.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserDto {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //token
    @NotNull
    private String jwtToken;

}
