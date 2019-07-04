package learningapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestAnswerDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.exceptions.base.InvalidTransitionException;
import learningapp.exceptions.base.NotFoundException;
import learningapp.handlers.StatusTransitionComputation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.VALIDATED;
import static learningapp.exceptions.ExceptionMessages.INVALID_TRANSITION_ERROR;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.factory.TestAnswerFactory.generateTestAnswerDtoBuilder;
import static learningapp.factory.TestQuestionFactory.generateTestQuestionDto;
import static learningapp.factory.TestQuestionFactory.generateTestQuestionDtoBuilder;
import static learningapp.mappers.GeneralMapper.uuidFromString;
import static learningapp.utils.TestConstants.INEXISTENT_ID;
import static learningapp.utils.TestConstants.INEXISTENT_USER;
import static learningapp.utils.TestConstants.UPDATED_ANSWER_TEXT;
import static learningapp.utils.TestConstants.UPDATED_QUESTION_TEXT;
import static learningapp.utils.TestConstants.USER_NAME;

public class QuestionIT extends BaseIntegrationTest {

    @Autowired
    private QuestionService testService;

    @Test
    public void givenValidTestQuestionDto_whenAddQuestion_thenUuidReturned() {

        //given
        User user = findOrCreateUser(USER_NAME);

        UUID topicId = createRandomSubjectWithTopic().getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDto();

        //when
        UUID testQuestionId = testService.addTestQuestion(topicId, user.getUsername(), testQuestionDto);

        //then
        assertNotNull(testQuestionId);

        Optional<TestQuestion> optionalTestQuestion = testQuestionRepository.findById(testQuestionId);
        assertTrue(optionalTestQuestion.isPresent());

        assertTestQuestionEquals(optionalTestQuestion.get(), testQuestionDto);
    }

    @Test
    public void givenInexistentTopicId_whenAddQuestion_thenExceptionIsThrown() {

        //given
        TestQuestionDto testQuestionDto = generateTestQuestionDto();
        User user = findOrCreateUser(USER_NAME);

        //when
        assertThatThrownByError(() -> testService.addTestQuestion(uuidFromString(INEXISTENT_ID), user.getUsername(), testQuestionDto),

                //then
                NotFoundException.class, TOPIC_NOT_FOUND);
    }

    @Test
    public void givenInexistentUsername_whenAddQuestion_thenExceptionIsThrown() {

        //given
        TestQuestionDto testQuestionDto = generateTestQuestionDto();

        UUID topicId = createRandomSubjectWithTopic().getId();

        //when
        assertThatThrownByError(() -> testService.addTestQuestion(topicId, INEXISTENT_USER, testQuestionDto),

                //then
                NotFoundException.class, USER_NOT_FOUND);
    }

    @Test
    public void givenInexistentQuestionId_whenGetQuestion_theExceptionIsThrown() {

        //when
        assertThatThrownByError(() -> testService.getQuestion(uuidFromString(INEXISTENT_ID)),

                //then
                NotFoundException.class,
                TEST_QUESTION_NOT_FOUND);
    }

    @Test
    public void givenExistentQuestionId_whenGetQuestion_theQuestiondtoIsReturned() {

        //given
        UUID testQuestionId = createRandomTestQuestion(createRandomSubjectWithTopic(), findOrCreateUser(USER_NAME), null).getId();

        //when
        TestQuestionDto testQuestionDto = testService.getQuestion(testQuestionId);

        //then
        assertNotNull(testQuestionDto);
    }

    @Test
    @Ignore
    public void givenValidTestQuestionDto_whenUpdateQuestion_thenUuidReturned() {

        //given
        User user = findOrCreateUser(USER_NAME);

        Topic topic = createRandomSubjectWithTopic();
        TestQuestion testQuestion = createRandomTestQuestion(topic, user, null);

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(testQuestion.getId())
                .topicId(topic.getId())
                .questionText(UPDATED_QUESTION_TEXT)
                .answerDtos(
                        Arrays.asList(generateTestAnswerDtoBuilder().
                                id(testAnswerRepository.findByQuestion(testQuestion).get(0).getId())
                                .answerText(UPDATED_ANSWER_TEXT)
                                .isCorrect(true)
                                .build()))
                .build();

        //when
        when(StatusTransitionComputation.isValidTransition(any(), any())).thenReturn(true);

        UUID updatedQuestionId = testService.updateTestQuestion(user.getUsername(), testQuestionDto);

        //then
        assertNotNull(updatedQuestionId);

        Optional<TestQuestion> optionalTestQuestion = testQuestionRepository.findByIdAndTopicId(updatedQuestionId, topic.getId());
        assertTrue(optionalTestQuestion.isPresent());

        TestQuestion updatedQuestion = optionalTestQuestion.get();

        assertTestQuestionEquals(updatedQuestion, testQuestionDto);
    }

    @Test
    public void givenInexistentTestQuestionId_whenUpdateTestQuestion_thenExceptionIsThrown() {

        //given
        User user = findOrCreateUser(USER_NAME);

        UUID topicId = createRandomSubjectWithTopic().getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(UUID.randomUUID())
                .build();

        //when
        assertThatThrownByError(() -> testService.updateTestQuestion(user.getUsername(), testQuestionDto),

                //then
                NotFoundException.class, TEST_QUESTION_NOT_FOUND);
    }

    @Test
    public void givenInexistentUserId_whenUpdateQuestion_thenExceptionIsThrown() {

        //given
        Topic topic = createRandomSubjectWithTopic();
        UUID questionId = createRandomTestQuestion(topic, findOrCreateUser(USER_NAME), null).getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(questionId)
                .build();

        //when
        assertThatThrownByError(() -> testService.updateTestQuestion(UUID.randomUUID().toString(), testQuestionDto),

                //then
                NotFoundException.class,
                USER_NOT_FOUND);
    }

    @Test
    @Ignore
    public void givenTestQuestionWithNewTopicId_whenUpdateTestQuestion_thenTestQuestionUpdated() {

        //given
        User user = findOrCreateUser(USER_NAME);

        Topic topic = createRandomSubjectWithTopic();
        Topic newTopic = createRandomSubjectWithTopic();
        TestQuestion testQuestion = createRandomTestQuestion(topic, user, null);

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(testQuestion.getId())
                .topicId(newTopic.getId())
                .build();

        //when
        testService.updateTestQuestion(user.getUsername(), testQuestionDto);

        //then
        Optional<TestQuestion> optionalTestQuestion = testQuestionRepository.findByIdAndTopicId(testQuestion.getId(), newTopic.getId());

        assertTrue(optionalTestQuestion.isPresent());

        assertTestQuestionEquals(optionalTestQuestion.get(), testQuestionDto);
    }

    @Test
    public void givenTwoQuestionsForTopic_whenGetAllQuestionsForTopic_thenDtoListReturned() {

        //given
        User user = findOrCreateUser(USER_NAME);

        Topic topic = createRandomSubjectWithTopic();
        createRandomTestQuestion(topic, user, PENDING);
        createRandomTestQuestion(topic, user, PENDING);

        //when
        List<TableQuestionDto> testQuestionDtos = testService.getAllQuestionsByTopic(topic.getId());

        //then
        assertNotNull(testQuestionDtos);
        assertEquals(testQuestionDtos.size(), 2);
    }

    @Test
    @Ignore
    public void givenAlreadyValidatedQuestion_whenValidateQuestion_thenExceptionIsThrown() {

        //given
        User user = findOrCreateUser(USER_NAME);

        Topic topic = createRandomSubjectWithTopic();
        UUID testQuestionId = createRandomTestQuestion(topic, user, VALIDATED).getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(testQuestionId)
                .topicId(topic.getId())
                .build();
        //when

        assertThatThrownByError(() -> testService.validateQuestion(testQuestionDto),
                //then
                InvalidTransitionException.class,
                INVALID_TRANSITION_ERROR + VALIDATED + " to " + VALIDATED);
    }

    @Test
    @Ignore
    public void givenValidQuestionDto_whenValidateQuestion_thenQuestionValidated() {

        //given
        User user = findOrCreateUser(USER_NAME);

        Topic topic = createRandomSubjectWithTopic();
        UUID testQuestionId = createRandomTestQuestion(topic, user, VALIDATED).getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(testQuestionId)
                .topicId(topic.getId())
                .build();

        //when
        when(StatusTransitionComputation.isValidTransition(any(), any())).thenReturn(true);
        testService.validateQuestion(testQuestionDto);

        //then
        Optional<TestQuestion> optionalTestQuestion = testQuestionRepository.findById(testQuestionId);

        assertTrue(optionalTestQuestion.isPresent());
        assertEquals(optionalTestQuestion.get().getStatus(), VALIDATED);

    }


    private void assertTestQuestionEquals(TestQuestion testQuestion, TestQuestionDto testQuestionDto) {

        if (testQuestionDto.getId() != null) {
            assertEquals(testQuestion.getId(), testQuestionDto.getId());
        }
        assertEquals(testQuestion.getText(), testQuestionDto.getQuestionText());
        assertEquals(testQuestion.getExplanation(), testQuestionDto.getExplanation());

        assertAnswersEquals(testAnswerRepository.findByQuestion(testQuestion), testQuestionDto.getAnswerDtos());
    }

    private void assertAnswersEquals(List<TestAnswer> answers, List<TestAnswerDto> answerDtos) {
        for (int i = 0; i < answerDtos.size(); ++i) {
            TestAnswer testAnswer = answers.get(i);
            TestAnswerDto testAnswerDto = answerDtos.get(i);

            assertEquals(testAnswer.getText(), testAnswerDto.getAnswerText());
            assertEquals(testAnswer.isCorrect(), testAnswerDto.isCorrect());
        }
    }

}
