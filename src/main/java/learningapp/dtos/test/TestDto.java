package learningapp.dtos.test;

import java.util.List;

import javax.validation.constraints.NotNull;

import learningapp.dtos.question.TestQuestionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TestDto {

    @NotNull
    private BaseTestDto baseTestDto;

    private List<TestQuestionDto> testQuestionDtoList;

}
