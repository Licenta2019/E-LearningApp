package learningapp.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private TestQuestion testQuestion;

}
