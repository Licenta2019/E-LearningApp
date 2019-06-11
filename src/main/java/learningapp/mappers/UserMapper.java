package learningapp.mappers;

import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;
import learningapp.entities.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static BaseUserDto toBaseUserDto(User user, String token) {
        BaseUserDto baseUserDto = new BaseUserDto();

        baseUserDto.setId(user.getId());
        baseUserDto.setJwtToken(token);
        baseUserDto.setNotificationsEnabled(user.isNotificationEnabled());
        baseUserDto.setUserRole(user.getUserRole());

        return baseUserDto;
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .notificationsEnabled(user.isNotificationEnabled())
                .password(user.getPassword())
                .username(user.getUsername())
                .build();
    }

    public static void toUser(User user, UserDto userDto) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setNotificationEnabled(userDto.isNotificationsEnabled());
        user.setPassword(userDto.getPassword());
    }

}
