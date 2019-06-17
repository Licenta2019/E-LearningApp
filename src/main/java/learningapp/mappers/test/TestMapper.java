package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.TestDto;
import learningapp.entities.Test;
import learningapp.entities.User;
import lombok.experimental.UtilityClass;

import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionDtoList;

@UtilityClass
public class TestMapper {

    public static BaseTestDto toBaseTestDto(Test test) {
        return BaseTestDto.builder()
                .id(test.getId())
                .name(test.getName())
                .author(test.getAuthor().getUsername())
                .creationDate(test.getCreationDate())
                .build();
    }

    public static List<BaseTestDto> toBaseTestDtoList(List<Test> tests) {
        return tests.stream()
                .map(TestMapper::toBaseTestDto)
                .collect(Collectors.toList());
    }

    public static Test toTestEntity(String name, User author) {
        return Test.builder()
                .name(name)
                .author(author)
                .build();
    }

    public static TestDto toTestDto(Test test) {
        return TestDto.builder()
                .baseTestDto(toBaseTestDto(test))
                .testQuestionDtoList(toTestQuestionDtoList(test.getQuestions()))
                .build();
    }

}
