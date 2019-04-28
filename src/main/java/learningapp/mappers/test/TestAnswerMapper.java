package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestAnswerMapper {

    public static TestAnswer toTestAnswerEntity(TestAnswerDto dto, TestQuestion question) {
        return TestAnswer.builder()
                .text(dto.getAnswerText())
                .isCorrect(dto.isCorrect())
                .question(question)
                .build();
    }

    public static void toTestAnswerEntity(TestAnswer testAnswer, TestAnswerDto dto) {
        testAnswer.setCorrect(dto.isCorrect());
        testAnswer.setText(dto.getAnswerText());
    }

    public static List<TestAnswer> toTestAnswerEntityList(List<TestAnswerDto> testAnswerDtos, TestQuestion testQuestion) {
        return testAnswerDtos.stream()
                .map(answerDto -> toTestAnswerEntity(answerDto, testQuestion))
                .collect(Collectors.toList());
    }

    public static TestAnswerDto toTestAnswerDto(TestAnswer testAnswer) {
        return TestAnswerDto.builder()
                .id(testAnswer.getId())
                .isCorrect(testAnswer.isCorrect())
                .answerText(testAnswer.getText())
                .build();
    }

    public static List<TestAnswerDto> toTestAnswerDtoList(List<TestAnswer> testAnswers) {
        return testAnswers.stream()
                .map(TestAnswerMapper::toTestAnswerDto)
                .collect(Collectors.toList());
    }
}
