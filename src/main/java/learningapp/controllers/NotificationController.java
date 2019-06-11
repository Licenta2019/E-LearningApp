package learningapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.NotificationApi;
import learningapp.dtos.notification.NotificationDto;
import learningapp.services.NotificationService;

import static learningapp.handlers.SecurityContextHolderAdapter.getCurrentUser;

@RestController
@RequestMapping("/user")
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @GetMapping("/notifications")
    public List<NotificationDto> getNotifications() {
        return notificationService.getAllNotifications(getCurrentUser());
    }

}
