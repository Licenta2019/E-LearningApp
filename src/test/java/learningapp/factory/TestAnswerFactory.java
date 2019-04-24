package learningapp.factory;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.entities.TestAnswer;

import static learningapp.utils.TestConstants.ANSWER_TEXT;

public class TestAnswerFactory {

    public static TestAnswerDto.TestAnswerDtoBuilder generateTestAnswerDtoBuilder() {
        return TestAnswerDto.builder()
                .answerText(ANSWER_TEXT)
                .isCorrect(true);
    }

    public static TestAnswerDto generateTestAnswerDto() {
        return generateTestAnswerDtoBuilder().build();
    }

    public static TestAnswer generateTestAnswer() {
        return TestAnswer.builder()
                .isCorrect(true)
                .text(ANSWER_TEXT)
                .build();
    }
}
