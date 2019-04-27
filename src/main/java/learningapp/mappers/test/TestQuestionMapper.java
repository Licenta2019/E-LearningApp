package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestQuestion;
import learningapp.entities.TestQuestionStatus;
import lombok.experimental.UtilityClass;

import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerDtoList;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntityList;

@UtilityClass
public class TestQuestionMapper {

    public static TestQuestion toTestQuestionEntity(TestQuestionDto dto) {
        TestQuestion testQuestion = TestQuestion.builder()
                .text(dto.getQuestionText())
                .explanation(dto.getExplanation())
                .status(dto.getStatus())
                .build();
        testQuestion.setAnswers(toTestAnswerEntityList(dto.getAnswerDtos(), testQuestion));

        return testQuestion;
    }

    public static void toTestQuestionEntity(TestQuestion testQuestion, TestQuestionDto dto, TestQuestionStatus status) {
        testQuestion.setText(dto.getQuestionText());
        testQuestion.setDifficulty(dto.getDifficulty());
        testQuestion.setExplanation(dto.getExplanation());
        testQuestion.setStatus(status);
    }

    public static TestQuestionDto toTestQuestionDto(TestQuestion testQuestion) {
        return TestQuestionDto.builder()
                .id(testQuestion.getId())
                .questionText(testQuestion.getText())
                .answerDtos(toTestAnswerDtoList(testQuestion.getAnswers()))
                .explanation(testQuestion.getExplanation())
                .status(testQuestion.getStatus())
                .topicId(testQuestion.getTopic().getId())
                .subjectId(testQuestion.getTopic().getSubject().getId())
                .build();
    }

    public static List<TestQuestionDto> toTestQuestionDtoList(List<TestQuestion> testQuestions) {
        return testQuestions.stream()
                .map(TestQuestionMapper::toTestQuestionDto)
                .collect(Collectors.toList());
    }

}
