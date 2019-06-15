package learningapp.mappers.test;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.test.BaseTestDto;
import learningapp.entities.Test;
import lombok.experimental.UtilityClass;

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

}
