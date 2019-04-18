package learningapp.dtos.question;

import learningapp.entities.TestQuestionStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionDto implements Serializable {

    private static final long serialVersionUID = 3L;

    private UUID id;

    //    @NotNull
    private UUID topicId;

    //    @NotNull
    private UUID subjectId;

    //    @NotNull
    private UUID studentId;

    @NotBlank
    private String questionText;

    @NotNull
    private List<TestAnswerDto> answerDtos;

    @NotNull
    private String explanation;

    private TestQuestionStatus status;

    private int difficulty;

    private String notificationMessage;

}
