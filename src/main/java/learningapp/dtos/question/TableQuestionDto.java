package learningapp.dtos.question;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import learningapp.entities.TestQuestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Enumerated(EnumType.STRING)
    private TestQuestionStatus status;

    @NotNull
    private LocalDate updateDate;
}
