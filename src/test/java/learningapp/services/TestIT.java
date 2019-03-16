package learningapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;
import learningapp.exceptions.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.factory.TestAnswerFactory.generateTestAnswerDtoBuilder;
import static learningapp.factory.TestQuestionFactory.generateTestQuestionDto;
import static learningapp.factory.TestQuestionFactory.generateTestQuestionDtoBuilder;
import static learningapp.mappers.GeneralMapper.uuidFromString;
import static learningapp.utils.TestConstants.INEXISTENT_ID;
import static learningapp.utils.TestConstants.UPDATED_ANSWER_TEXT;
import static learningapp.utils.TestConstants.UPDATED_QUESTION_TEXT;

public class TestIT extends BaseIntegrationTest {

    @Autowired
    private TestService testService;

    @Test
    public void givenValidTestQuestionDto_whenAddQuestion_thenUuidReturned() {

        //given
        UUID topicId = createRandomSubjectWithTopic().getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDto();

        //when
        UUID testQuestionId = testService.addTestQuestion(topicId, testQuestionDto);

        //then
        assertNotNull(testQuestionId);

        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionId).get();

        assertTestQuestionEquals(testQuestion, testQuestionDto);
    }

    @Test
    public void givenInexistentTopicId_whenAddQuestion_thenExceptionIsThrown() {

        //given
        TestQuestionDto testQuestionDto = generateTestQuestionDto();

        //when
        assertThatThrownByError(() -> testService.addTestQuestion(uuidFromString(INEXISTENT_ID), testQuestionDto),

                //then
                NotFoundException.class, TOPIC_NOT_FOUND);
    }

    @Test
    public void givenValidTestQuestionDto_whenUpdateQuestion_thenUuidReturned() {

        //given
        Topic topic = createRandomSubjectWithTopic();
        TestQuestion testQuestion = createRandomTestQuestion(topic);

        TestQuestionDto testQuestionDto = generateTestQuestionDtoBuilder()
                .id(testQuestion.getId())
                .questionText(UPDATED_QUESTION_TEXT)
                .answerDtos(
                        Arrays.asList(generateTestAnswerDtoBuilder().
                                id(testQuestion.getAnswers().get(0).getId())
                                .answerText(UPDATED_ANSWER_TEXT)
                                .isCorrect(true)
                                .build()))
                .build();

        //when
        UUID updatedQuestionId = testService.updateTestQuestion(topic.getId(), testQuestionDto);

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
        UUID topicId = createRandomSubjectWithTopic().getId();

        TestQuestionDto testQuestionDto = generateTestQuestionDto();

        //when
        assertThatThrownByError(() -> testService.updateTestQuestion(topicId, testQuestionDto),
                //then

                NotFoundException.class, TEST_QUESTION_NOT_FOUND);
    }

    @Test
    @Transactional
    public void givenTwoQuestionsForTopic_whenGetAllQuestionsForTopic_thenDtoListReturned() {

        //given
        Topic topic = createRandomSubjectWithTopic();
        createRandomTestQuestion(topic);
        createRandomTestQuestion(topic);

        //when
        List<TestQuestionDto> testQuestionDtos = testService.getAllQuestionsByTopic(topic.getId());

        //then
        assertNotNull(testQuestionDtos);
        assertEquals(testQuestionDtos.size(), 2);
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
