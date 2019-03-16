package learningapp.dtos.question;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAnswerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private UUID id;

    @NotBlank
    private String answerText;

    @NotNull
    @JsonProperty
    private boolean isCorrect;

}
