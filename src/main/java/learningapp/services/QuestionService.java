package learningapp.services;

import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestQuestionDto;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    /**
     * Method that saves a test question dto via a specific test question dto.
     *
     * @param topicId         - id of the correlated topic
     * @param testQuestionDto - test question's data
     * @return
     */
    UUID addTestQuestion(UUID topicId, TestQuestionDto testQuestionDto);

    /**
     * Method that updates a test question via a specific test question dto.
     *
     * @param testQuestionDto - data for new question dto
     * @param topicId
     * @return
     */
    UUID updateTestQuestion(UUID topicId, TestQuestionDto testQuestionDto);

    /**
     * Return all questions for a specific topic.
     *
     * @param topicId - the topic's id
     * @return
     */
    List<TableQuestionDto> getAllQuestionsByTopic(UUID topicId);

    /**
     * Validate a test question via it's dto.
     * Perform an update and after set question status to VALIDATED.
     *
     * @param testQuestionDto
     */
    void validateQuestion(TestQuestionDto testQuestionDto);

    /**
     * Retrieve a specific question via it's id
     *
     * @param uuid
     * @return
     */
    TestQuestionDto getQuestion(UUID uuid);

}
