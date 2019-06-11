package learningapp.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import learningapp.entities.notification.NotificationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "application_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotNull
    private boolean notificationEnabled = false;

    @OneToMany(mappedBy = "user")
    private List<NotificationUser> notificationUsers;

}
