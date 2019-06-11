package learningapp.entities.notification;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUserId implements Serializable {

    private UUID userId;

    private UUID notificationId;

}
