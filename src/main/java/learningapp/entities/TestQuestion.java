package learningapp.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

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
    private LocalDateTime created;

    @ManyToOne(optional = false)
    private Topic topic;

    @ManyToOne(optional = false)
    private User student;

    @NotBlank
    private String text;

    @OneToMany(mappedBy = "question")
    private List<TestAnswer> answers;

    @Min(0)
    @Max(10)
    @Builder.Default
    private int difficulty = 0;

    @NotBlank
    private String explanation;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TestQuestionStatus status = TestQuestionStatus.PENDING;

}
