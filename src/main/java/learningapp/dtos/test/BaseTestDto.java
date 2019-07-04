package learningapp.dtos.test;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import learningapp.entities.TestDifficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BaseTestDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String author;

    @NotNull
    private TestDifficulty difficulty;

}
