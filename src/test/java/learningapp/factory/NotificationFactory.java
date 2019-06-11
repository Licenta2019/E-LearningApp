package learningapp.factory;

import java.util.Collections;
import java.util.UUID;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.entities.notification.NotificationType;

public class NotificationFactory {

    public static NotificationCreationDto generateNotificationCreationDto(UUID userId) {
        return NotificationCreationDto.builder()
                .message("message")
                .notificationType(NotificationType.INFO)
                .users(Collections.singletonList(userId))
                .build();
    }
}
