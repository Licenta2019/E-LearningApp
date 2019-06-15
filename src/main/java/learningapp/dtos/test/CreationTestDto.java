package learningapp.dtos.test;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreationTestDto {

    @NotNull
    private String name;

    @NotNull
    private List<TopicTestDto> topicTestDtoList;

}
