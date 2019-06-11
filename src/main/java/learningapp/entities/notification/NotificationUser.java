package learningapp.entities.notification;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import learningapp.entities.User;
import lombok.Setter;

@Entity
@Setter
public class NotificationUser {

    @EmbeddedId
    private NotificationUserId notificationUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("notificationId")
    private Notification notification;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    private boolean wasSeen = false;

}
