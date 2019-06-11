package learningapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learningapp.entities.notification.NotificationUser;
import learningapp.entities.notification.NotificationUserId;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, NotificationUserId> {

}
