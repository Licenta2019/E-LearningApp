package learningapp.dtos.subject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
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
public class SubjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    @NotBlank
    private String name;

    @NotNull
    private List<TopicDto> topicDtos;

}
