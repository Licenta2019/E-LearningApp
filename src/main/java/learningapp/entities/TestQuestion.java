package learningapp.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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

    @ManyToOne
    private Topic topic;

    @NotBlank
    private String text;

    @OneToMany(mappedBy = "question")
    private List<TestAnswer> answers;

    private boolean wasValidated = false;

    @Min(0)
    @Max(10)
    private int difficulty = 0;
}
