package learningapp.dtos.question;

import java.io.Serializable;

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
public class TestAnswerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    @NotBlank
    private String text;

    @NotNull
    private boolean isCorrect;

}
