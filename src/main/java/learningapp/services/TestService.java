package learningapp.services;

import java.util.UUID;

import learningapp.dtos.question.TestQuestionDto;

public interface TestService {

    /**
     * Method that saves a test question dto via a specific test question dto.
     *
     * @param topicId         - id of the correlated topic
     * @param testQuestionDto - test question's data
     * @return
     */
    UUID addTestQuestion(UUID topicId, TestQuestionDto testQuestionDto);

}
