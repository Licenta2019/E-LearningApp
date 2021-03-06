package learningapp.dtos.subject;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {

    private static final long serialVersionUID = 2L;

    private UUID id;

    @NotBlank
    private String name;

}
