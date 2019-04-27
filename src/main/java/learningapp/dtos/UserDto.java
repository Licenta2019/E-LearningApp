package learningapp.dtos;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import learningapp.entities.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
