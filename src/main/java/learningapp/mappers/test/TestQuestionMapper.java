package learningapp.mappers.test;

import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestQuestion;
import lombok.experimental.UtilityClass;

import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntityList;

@UtilityClass
public class TestQuestionMapper {

    public static TestQuestion toTestQuestionEntity(TestQuestionDto dto) {
        return TestQuestion.builder()
                .text(dto.getText())
                .answers(toTestAnswerEntityList(dto.getAnswerDtos()))
                .build();
    }

}
