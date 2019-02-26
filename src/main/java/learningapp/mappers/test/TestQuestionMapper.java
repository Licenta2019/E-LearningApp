package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestQuestion;
import lombok.experimental.UtilityClass;

import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerDtoList;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntityList;

@UtilityClass
public class TestQuestionMapper {

    public static TestQuestion toTestQuestionEntity(TestQuestionDto dto) {
        return TestQuestion.builder()
                .text(dto.getQuestionText())
                .answers(toTestAnswerEntityList(dto.getAnswerDtos()))
                .build();
    }

    public static void toTestQuestionEntity(TestQuestion testQuestion, TestQuestionDto dto) {
        testQuestion.setText(dto.getQuestionText());
//        testQuestion.setDifficulty();
    }

    public static TestQuestionDto toTestQuestionDto(TestQuestion testQuestion) {
        return TestQuestionDto.builder()
                .id(testQuestion.getId())
                .questionText(testQuestion.getText())
                .answerDtos(toTestAnswerDtoList(testQuestion.getAnswers()))
                .build();
    }

    public static List<TestQuestionDto> toTestQuestionDtoList(List<TestQuestion> testQuestions) {
        return testQuestions.stream()
                .map(TestQuestionMapper::toTestQuestionDto)
                .collect(Collectors.toList());
    }

}
