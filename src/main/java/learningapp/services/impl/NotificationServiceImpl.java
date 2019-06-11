package learningapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.dtos.notification.NotificationDto;
import learningapp.entities.User;
import learningapp.entities.notification.Notification;
import learningapp.entities.notification.NotificationUser;
import learningapp.entities.notification.NotificationUserId;
import learningapp.exceptions.base.NotFoundException;
import learningapp.repositories.NotificationRepository;
import learningapp.repositories.NotificationUserRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.NotificationService;

import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.mappers.NotificationMapper.toNotificationDtoList;
import static learningapp.mappers.NotificationMapper.toNotificationEntity;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   NotificationUserRepository notificationUserRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationUserRepository = notificationUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Async
    @Transactional
    public void saveNotification(NotificationCreationDto notificationDto) {
        Notification notification = toNotificationEntity(notificationDto);

        notificationRepository.save(notification);

        notification.setNotificationUsers(saveNotificationUsers(notification, notificationDto.getUsers()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDto> getAllNotifications(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        List<Notification> notifications = notificationRepository.findAllByUserAndWasSeenFalse(user);

        return toNotificationDtoList(notifications);
    }

    private List<NotificationUser> saveNotificationUsers(Notification notification, List<UUID> userIds) {
        List<NotificationUser> notificationUsers = new ArrayList<>();

        userIds.forEach(id -> {
            NotificationUser notificationUser = new NotificationUser();
            notificationUser.setNotification(notification);
            notificationUser.setUser(userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND)));

            notificationUser.setNotificationUserId(new NotificationUserId(id, notification.getId()));

            notificationUsers.add(notificationUser);
        });

        return notificationUserRepository.saveAll(notificationUsers);
    }

}
