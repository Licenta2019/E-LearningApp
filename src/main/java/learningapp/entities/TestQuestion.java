package learningapp.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

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
