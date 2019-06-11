package learningapp.services;

import java.util.List;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.dtos.notification.NotificationDto;

public interface NotificationService {

    /**
     * Save a notification via a specific notification dto.
     * This function also saves corellated notificationUsers.
     *
     * @param notificationDto
     */
    void saveNotification(NotificationCreationDto notificationDto);

    /**
     * Get all notifications for given user.
     *
     * @param username
     * @return
     */
    List<NotificationDto> getAllNotifications(String username);

}
