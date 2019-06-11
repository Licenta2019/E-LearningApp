package learningapp.apis;

import java.util.List;

import io.swagger.annotations.Api;
import learningapp.dtos.notification.NotificationDto;

@Api(tags = "Notification API")
public interface NotificationApi {

    List<NotificationDto> getNotifications();

}
