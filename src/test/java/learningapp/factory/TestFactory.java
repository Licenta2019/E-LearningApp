package learningapp.factory;

import java.util.Arrays;

import learningapp.dtos.question.TestQuestionDto;

import static learningapp.factory.TestAnswerFactory.generateTestAnswerDto;
import static learningapp.utils.TestConstants.EXPLANATION;
import static learningapp.utils.TestConstants.QUESTION_TEXT;

public class TestFactory {
    public static TestQuestionDto.TestQuestionDtoBuilder generateTestQuestionDtoBuilder() {
        return TestQuestionDto.builder()
                .questionText(QUESTION_TEXT)
                .explanation(EXPLANATION)
                .answerDtos(Arrays.asList(generateTestAnswerDto()));
    }

    public static TestQuestionDto generateTestQuestionDto() {
        return generateTestQuestionDtoBuilder().build();
    }

}
