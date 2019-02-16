package learningapp.dtos.question;

import java.io.Serializable;
import java.util.List;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionDto implements Serializable {

    private static final long serialVersionUID = 3L;

    @NotBlank
    private String text;

    @OneToMany(mappedBy = "question")
    private List<TestAnswerDto> answerDtos;

}
