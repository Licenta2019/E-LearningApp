package learningapp.services;

import java.util.List;
import java.util.UUID;

import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestQuestionDto;

public interface QuestionService {

    /**
     * Method that saves a test question dto via a specific test question dto.
     *
     * @param topicId         - id of the correlated topic
     * @param currentUser     - logged-in user
     * @param testQuestionDto - test question's data
     * @return
     */
    UUID addTestQuestion(UUID topicId, String currentUser, TestQuestionDto testQuestionDto);

    /**
     * Method that updates a test question via a specific test question dto.
     *
     * @param currentUser     - logged-in user
     * @param testQuestionDto - data for new question dto
     * @return
     */
    UUID updateTestQuestion(String currentUser, TestQuestionDto testQuestionDto);

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

    /**
     * Retrieve all questions related to subjects teached by a specific professor
     *
     * @return
     */
    List<TableQuestionDto> getAllQuestionForProfessor(UUID professorId);

    /**
     * Retrieve all questions added by a specific student
     *
     * @return
     */
    List<TableQuestionDto> getAllQuestionForStudent(UUID studentId);

    /**
     * Return number of all related questions with given user that are in a non-terminal status
     *
     * @param userId
     * @return
     */
    int getNotificationsCount(UUID userId);
}
