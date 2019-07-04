package learningapp.dtos.test;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TakeTestDto implements Serializable {

    @NotNull
    private UUID questionId;

    @NotNull
    private List<UUID> answers;

}
