package learningapp.dtos;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import learningapp.entities.Subject;
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

    @NotNull
    private String name;

    @ManyToOne
    private Subject subject;

}
