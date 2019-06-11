package learningapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.entities.User;
import learningapp.entities.notification.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query(value = "Select n from Notification n join n.notificationUsers nu" +
            " where nu.wasSeen = false and nu.user = ?1")
    List<Notification> findAllByUserAndWasSeenFalse(User user);

}
