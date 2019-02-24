package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.entities.TestAnswer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestAnswerMapper {

    public static TestAnswer toTestAnswerEntity(TestAnswerDto dto) {
        return TestAnswer.builder()
                .text(dto.getAnswerText())
                .isCorrect(dto.isCorrect())
                .build();
    }

    public static List<TestAnswer> toTestAnswerEntityList(List<TestAnswerDto> testAnswerDtos) {
        return testAnswerDtos.stream()
                .map(TestAnswerMapper::toTestAnswerEntity)
                .collect(Collectors.toList());
    }
}
