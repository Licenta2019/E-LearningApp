package learningapp.exceptions;

import javax.validation.constraints.NotNull;

import lombok.Builder;

@Builder
public class ResponseEntity {

    @NotNull
    public String message;

}
