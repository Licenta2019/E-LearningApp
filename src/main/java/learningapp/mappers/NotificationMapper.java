package learningapp.mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.dtos.notification.NotificationDto;
import learningapp.entities.notification.Notification;
import learningapp.entities.notification.NotificationType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificationMapper {

    public static Notification toNotificationEntity(NotificationCreationDto notificationDto) {
        return Notification.builder()
                .message(notificationDto.getMessage())
                .type(notificationDto.getNotificationType())
                .build();
    }

    public static NotificationCreationDto toNotificationCreationDto(String message, NotificationType notificationType, List<UUID> users) {
        return NotificationCreationDto.builder()
                .message(message)
                .notificationType(notificationType)
                .users(users)
                .build();
    }

    public static NotificationDto toNotificationDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .creatioDate(notification.getCreationDate())
                .message(notification.getMessage())
                .notificationType(notification.getType())
                .build();
    }

    public static List<NotificationDto> toNotificationDtoList(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationMapper::toNotificationDto)
                .collect(Collectors.toList());
    }

}
