package learningapp.dtos.notification;


import java.util.List;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import learningapp.entities.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCreationDto {

    @NotNull
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @NotNull
    private String message;

    @NotNull
    private List<UUID> users;

}
