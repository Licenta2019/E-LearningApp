package learningapp.services;

import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.dtos.notification.NotificationDto;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;

import static org.junit.Assert.assertFalse;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.NotificationFactory.generateNotificationCreationDto;
import static learningapp.utils.TestConstants.INEXISTENT_USER;
import static learningapp.utils.TestConstants.USER_NAME;

public class NotificationIT extends BaseIntegrationTest {

    @Autowired
    NotificationService notificationService;

    @Test
    @Ignore
    public synchronized void givenInexistentUser_whenSaveNotification_thenExceptionIsThrown() throws InterruptedException {

        //given
        NotificationCreationDto notificationCreationDto = generateNotificationCreationDto(UUID.randomUUID());

        //when

        assertThatThrownByError(() -> notificationService.saveNotification(notificationCreationDto),

                //then
                NotFoundException.class,
                USER_NOT_FOUND);
    }

    @Test
    @Ignore
    public void givenValidNotificationCreationDto_whenSaveNotification_thenExceptionIsThrown() {

        //given
        User user = findOrCreateUser(USER_NAME);
        NotificationCreationDto notificationCreationDto = generateNotificationCreationDto(user.getId());

        //when
        synchronized (notificationService) {
            notificationService.saveNotification(notificationCreationDto);

            List<NotificationDto> notificationDtos = notificationService.getAllNotifications(USER_NAME);
            assertFalse(notificationDtos.isEmpty());
        }
    }

    @Test
    public void givenInexistentUser_whenGetAllNotification_thenNotificationsRetreieved() {

        //when
        assertThatThrownByError(() -> notificationService.getAllNotifications(INEXISTENT_USER),

                //then
                NotFoundException.class,
                USER_NOT_FOUND);
    }

    @Test
    public void givenExistentUser_whenGetAllNotification_thenNotificationsRetreieved() {

        //given
        User user = findOrCreateUser(USER_NAME);
        createRandomNotification(user);

        //when
        List<NotificationDto> notificationDtos = notificationService.getAllNotifications(USER_NAME);

        //then
        assertFalse(notificationDtos.isEmpty());
    }

}
