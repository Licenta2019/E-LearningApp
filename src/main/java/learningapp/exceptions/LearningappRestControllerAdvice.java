package learningapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import learningapp.exceptions.base.DuplicateEntityException;
import learningapp.exceptions.base.InvalidFormatException;
import learningapp.exceptions.base.InvalidTransitionException;
import learningapp.exceptions.base.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class LearningappRestControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleException(NotFoundException e) {
        log.error("not found exception!");
        return ResponseEntity.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public ResponseEntity handleException(DuplicateEntityException e) {
        log.error("duplicate entity exception!");
        return ResponseEntity.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity handleException(InvalidFormatException e) {
        log.error("invalid format exception!");
        return ResponseEntity.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity handleException(BadCredentialsException e) {
        log.error("bad credentials exception!");
        return ResponseEntity.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity handleException(InvalidTransitionException e) {
        log.error("invalid transition exception!");
        return ResponseEntity.builder()
                .message(e.getMessage())
                .build();
    }

}
