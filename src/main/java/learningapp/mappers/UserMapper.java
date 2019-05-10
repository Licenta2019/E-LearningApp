package learningapp.mappers;

import learningapp.dtos.UserDto;
import learningapp.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static UserDto toUserDto(User user, String token) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .userRole(user.getUserRole())
                .jwtToken(token)
                .build();
    }

}
