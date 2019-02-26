package learningapp.factory;

import java.util.Arrays;

import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestQuestion;

import static learningapp.factory.TestAnswerFactory.generateTestAnswerDto;
import static learningapp.utils.TestConstants.QUESTION_TEXT;

public class TestQuestionFactory {

    public static TestQuestionDto.TestQuestionDtoBuilder generateTestQuestionDtoBuilder() {
        return TestQuestionDto.builder()
                .questionText(QUESTION_TEXT)
                .answerDtos(Arrays.asList(generateTestAnswerDto()));
    }

    public static TestQuestionDto generateTestQuestionDto() {
        return generateTestQuestionDtoBuilder().build();
    }

    public static TestQuestion generateTestQuestion() {
        return TestQuestion.builder()
                .text(QUESTION_TEXT)
                .difficulty(10)
                .wasValidated(false)
                .build();
    }

}
