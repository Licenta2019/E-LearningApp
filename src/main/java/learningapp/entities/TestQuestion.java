package learningapp.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class TestQuestion extends BaseEntity {

    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate updated;

    @ManyToOne(optional = false)
    private Topic topic;

    @ManyToOne(optional = false)
    private User author;

    @NotBlank
    @Column(length = 2047)
    private String text;

    @OneToMany(mappedBy = "question")
    private List<TestAnswer> answers;

    @Min(0)
    @Max(10)
    @Builder.Default
    private int difficulty = 0;

    @NotBlank
    @Column(length = 2047)
    private String explanation;

    private String notificationMessage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TestQuestionStatus status = TestQuestionStatus.PENDING;

    public LocalDate getLastUpdateDate() {
        return getUpdated() != null ? getUpdated() : getCreated();
    }

}
