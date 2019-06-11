package learningapp.entities.notification;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import learningapp.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @OneToMany(mappedBy = "notification")
    private List<NotificationUser> notificationUsers;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @NotNull
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

}
