package learningapp.dtos.test;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import learningapp.entities.TestDifficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicTestDto {

    @NotNull
    private UUID topicId;

    @NotNull
    private int questionsNumber;

    @NotNull
    private TestDifficulty difficulty;

}
