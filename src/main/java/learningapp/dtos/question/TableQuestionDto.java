package learningapp.dtos.question;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableQuestionDto {

    @NotNull
    private UUID id;

    @NotNull
    private String questionText;

    @NotNull
    private String subject;

    @NotNull
    private String topic;

    @NotNull
    private String author;

    @NotNull
    private LocalDate creationDate;
}
