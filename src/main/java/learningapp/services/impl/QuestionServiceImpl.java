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
import learningapp.entities.TestQuestionStatus;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.exceptions.base.InvalidTransitionException;
import learningapp.exceptions.base.NotFoundException;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.QuestionService;
import lombok.extern.slf4j.Slf4j;

import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.VALIDATED;
import static learningapp.exceptions.ExceptionMessages.INVALID_TRANSITION_ERROR;
import static learningapp.exceptions.ExceptionMessages.TEST_ANSWER_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.handlers.StatusTransitionComputation.getNextStatus;
import static learningapp.handlers.StatusTransitionComputation.isValidTransition;
import static learningapp.handlers.StatusTransitionComputation.nonFinalStatuses;
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

    public QuestionServiceImpl(TopicRepository topicRepository,
                               TestQuestionRepository testQuestionRepository,
                               TestAnswerRepository testAnswerRepository,
                               UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
        this.userRepository = userRepository;
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
    public UUID addTestQuestion(UUID topicId, String currentUser, TestQuestionDto testQuestionDto) {
        Topic topic = getTopicEntity(topicId);

        User author = userRepository.findByUsername(currentUser)
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
        return testQuestionRepository.findAllByProfessorAndStatus(professorId, nonFinalStatuses);
    }


    @Override
    public List<TableQuestionDto> getAllQuestionForStudent(UUID studentId) {
        return testQuestionRepository.findAllByStudentAndStatus(studentId, nonFinalStatuses);
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
    public UUID updateTestQuestion(String currentUser, TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = getTestQuestionEntity(testQuestionDto.getId());

        User user = userRepository.findByUsername(currentUser)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Topic topic = getTopicEntity(testQuestionDto.getTopicId());
        TestQuestionStatus nextStatus = getNextStatus(testQuestion.getStatus(), user.getUserRole());
        toTestQuestionEntity(testQuestion, testQuestionDto, nextStatus);

        testQuestion.setTopic(topic);

        testQuestionDto.getAnswerDtos().forEach((testAnswerDto) -> updateTestAnswer(testAnswerDto, testQuestion)); //possible changes

        return testQuestionRepository.save(testQuestion).getId();
    }

    @Override
    @Transactional
    public void validateQuestion(TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = getTestQuestionEntity(testQuestionDto.getId());

        if (!isValidTransition(testQuestion.getStatus(), VALIDATED)) {
            throw new InvalidTransitionException(INVALID_TRANSITION_ERROR + testQuestion.getStatus() + " to " + VALIDATED);
        }

        testQuestionDto.getAnswerDtos().forEach((testAnswerDto) -> updateTestAnswer(testAnswerDto, testQuestion));

        toTestQuestionEntity(testQuestion, testQuestionDto, VALIDATED);

        testQuestionRepository.save(testQuestion);
    }

    @Override
    public List<TableQuestionDto> getAllQuestionsByTopic(UUID topicId) {
        Topic topic = getTopicEntity(topicId);

        return toTableQuestionDtoList(testQuestionRepository.findAllByTopicAndStatus(topic, PENDING));
    }

    private void updateTestAnswer(TestAnswerDto testAnswerDto, TestQuestion question) {
        if (testAnswerDto.getId() != null) {
            TestAnswer testAnswer = testAnswerRepository.findById(testAnswerDto.getId())
                    .orElseThrow(() -> new NotFoundException(TEST_ANSWER_NOT_FOUND));

            toTestAnswerEntity(testAnswer, testAnswerDto);

            testAnswerRepository.save(testAnswer);
        } else {
            saveNewTestAnswer(testAnswerDto, question);
        }
    }

    private void saveNewTestAnswer(TestAnswerDto answerDto, TestQuestion question) {
        TestAnswer answer = new TestAnswer();
        toTestAnswerEntity(answer, answerDto);

        answer.setQuestion(question);
        testAnswerRepository.save(answer);
    }

    private void addTestAnswer(TestQuestion question) {
        question.getAnswers().forEach((answer) -> {
            answer.setQuestion(question);
            testAnswerRepository.save(answer);
        });
    }

}
