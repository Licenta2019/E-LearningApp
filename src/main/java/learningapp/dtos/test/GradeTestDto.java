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
@AllArgsConstructor
@NoArgsConstructor
public class GradeTestDto implements Serializable {

    @NotNull
    private UUID testId;

    @NotNull
    private List<TakeTestDto> questions;
}
