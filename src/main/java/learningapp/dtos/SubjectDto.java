package learningapp.dtos;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    private List<TopicDto> topicDtos;

}
