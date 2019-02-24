package learningapp.dtos.question;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private UUID id;

    @NotBlank
    private String questionText;

    @NotNull
    private List<TestAnswerDto> answerDtos;

}
