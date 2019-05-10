package learningapp.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.QuestionService;
import lombok.extern.slf4j.Slf4j;

import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.REQUESTED_CHANGES;
import static learningapp.entities.TestQuestionStatus.VALIDATED;
import static learningapp.exceptions.ExceptionMessages.INVALID_TRANSITION_ERROR;
import static learningapp.exceptions.ExceptionMessages.TEST_ANSWER_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.handlers.SecurityContextHolderAdapter.getCurrentUser;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntity;
import static learningapp.mappers.test.TestQuestionMapper.toTableQuestionDtoList;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionDto;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionEntity;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final TopicRepository topicRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final TestAnswerRepository testAnswerRepository;

    private final UserRepository userRepository;

    private final StatusTransitionComputation statusTransitionComputation;

    public QuestionServiceImpl(TopicRepository topicRepository,
                               TestQuestionRepository testQuestionRepository,
                               TestAnswerRepository testAnswerRepository,
                               UserRepository userRepository,
                               StatusTransitionComputation statusTransitionComputation) {
        this.topicRepository = topicRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
        this.userRepository = userRepository;
        this.statusTransitionComputation = statusTransitionComputation;
    }

    private Topic getTopicEntity(UUID topicId) {
        return topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));
    }

    private TestQuestion getTestQuestionEntity(UUID id) {
        return testQuestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TEST_QUESTION_NOT_FOUND));
    }

    @Override
    @Transactional
    public UUID addTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {

        Topic topic = getTopicEntity(topicId);

        User author = userRepository.findByUsername(getCurrentUser())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        TestQuestion testQuestion = toTestQuestionEntity(testQuestionDto);

        testQuestion.setTopic(topic);
        testQuestion.setAuthor(author);

        addTestAnswer(testQuestion);

        return testQuestionRepository.save(testQuestion).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public TestQuestionDto getQuestion(UUID id) {
        TestQuestion testQuestion = getTestQuestionEntity(id);

        return toTestQuestionDto(testQuestion);
    }

    @Override
    public List<TableQuestionDto> getAllQuestionForProfessor(UUID professorId) {
        return testQuestionRepository.findAllByProfessor(professorId);
    }

    @Override
    public List<TableQuestionDto> getAllQuestionForStudent(UUID studentId) {
        return testQuestionRepository.findAllByStudent(studentId);
    }

    @Override
    public int getNotificationsCount(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        switch (user.getUserRole()) {
            case PROFESSOR:
                return testQuestionRepository.getNotificationsCountForProfessor(userId);
            case STUDENT:
                return testQuestionRepository.getNotificationsCountForStudent(userId);
            default:
                return 0;
        }
    }

    @Override
    @Transactional
    public UUID updateTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = getTestQuestionEntity(testQuestionDto.getId());

        toTestQuestionEntity(testQuestion, testQuestionDto, PENDING);

        if (testQuestionDto.getNotificationMessage() != null) {
            testQuestion.setStatus(REQUESTED_CHANGES);
            testQuestionDto.getAnswerDtos().forEach(this::updateTestAnswer); //possible changes made by professor
        } else if (testQuestion.getStatus().equals(REQUESTED_CHANGES)) {
            updateTestQuestionAfterNotification(testQuestion);
            addTestAnswer(testQuestion);
        }

        return testQuestionRepository.save(testQuestion).getId();
    }

    @Override
    @Transactional
    public void validateQuestion(TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = getTestQuestionEntity(testQuestionDto.getId());

//        if (!statusTransitionComputation.isValidTransition(testQuestion.getStatus(), VALIDATED)) {
//            throw new InvalidTransitionException(INVALID_TRANSITION_ERROR + testQuestion.getStatus() + "to " + VALIDATED);
//        }

        testQuestionDto.getAnswerDtos().forEach(this::updateTestAnswer);

        toTestQuestionEntity(testQuestion, testQuestionDto, VALIDATED);

        testQuestionRepository.save(testQuestion);
    }

    private void updateTestQuestionAfterNotification(TestQuestion testQuestion) {
        testAnswerRepository.deleteAllByQuestion(testQuestion);

        testQuestion.setStatus(PENDING);
    }

    @Override
    public List<TableQuestionDto> getAllQuestionsByTopic(UUID topicId) {
        Topic topic = getTopicEntity(topicId);

        return toTableQuestionDtoList(testQuestionRepository.findAllByTopicAndStatus(topic, PENDING));
    }

    private void updateTestAnswer(TestAnswerDto testAnswerDto) {
        TestAnswer testAnswer = testAnswerRepository.findById(testAnswerDto.getId())
                .orElseThrow(() -> new NotFoundException(TEST_ANSWER_NOT_FOUND));

        toTestAnswerEntity(testAnswer, testAnswerDto);

        testAnswerRepository.save(testAnswer);
    }

    private void addTestAnswer(TestQuestion question) {
        question.getAnswers().forEach((answer) -> {
            answer.setQuestion(question);
            testAnswerRepository.save(answer);
        });
    }

}
