package learningapp.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.QuestionService;

import static learningapp.entities.TestQuestionStatus.PENDING;
import static learningapp.entities.TestQuestionStatus.REQUESTED_CHANGES;
import static learningapp.entities.TestQuestionStatus.VALIDATED;
import static learningapp.exceptions.ExceptionMessages.STUDENT_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_ANSWER_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntity;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionDtoList;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionEntity;

@Service
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

    @Override
    @Transactional
    public UUID addTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));

        User student = userRepository.findById(testQuestionDto.getStudentId())
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
        TestQuestion testQuestion = toTestQuestionEntity(testQuestionDto);

        testQuestion.setTopic(topic);
        testQuestion.setStudent(student);
        testQuestion = testQuestionRepository.save(testQuestion);

        for (TestAnswerDto testAnswerDto : testQuestionDto.getAnswerDtos()) {
            addTestAnswer(testAnswerDto, testQuestion);
        }

        return testQuestion.getId();
    }

    @Override
    @Transactional
    public UUID updateTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        //TODO(fix mapper for update)
        //TODO(add subject and topic modifications here)
        TestQuestion testQuestion = testQuestionRepository.findByIdAndTopicId(testQuestionDto.getId(), topicId)
                .orElseThrow(() -> new NotFoundException(TEST_QUESTION_NOT_FOUND));

        testQuestion.setText(testQuestionDto.getQuestionText());

        if (testQuestionDto.getStatus().equals(PENDING)) {
            updateTestQuestionEntity(testQuestionDto);
        } else {
            updateTestQuestionAfterNotification(testQuestion, testQuestionDto);
        }

        return testQuestionRepository.save(testQuestion).getId();
    }

    @Override
    @Transactional
    public void validateQuestion(TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionDto.getId())
                .orElseThrow(() -> new NotFoundException(TEST_QUESTION_NOT_FOUND));

        updateTestQuestionEntity(testQuestionDto); //possible changes made by professor
        testQuestion.setStatus(VALIDATED); // set status to validated - now it's a valid question

        testQuestionRepository.save(testQuestion);
    }

    private void updateTestQuestionEntity(TestQuestionDto testQuestionDto) {
        testQuestionDto.setStatus(REQUESTED_CHANGES); // set status to requested_changes
        testQuestionDto.getAnswerDtos().forEach(this::updateTestAnswer);
    }

    private void updateTestQuestionAfterNotification(TestQuestion testQuestion, TestQuestionDto testQuestionDto) {
        testAnswerRepository.deleteAllByQuestion(testQuestion);

        testQuestion.setStatus(PENDING);//reset status to pending
        for (TestAnswerDto testAnswerDto : testQuestionDto.getAnswerDtos()) {
            addTestAnswer(testAnswerDto, testQuestion);
        }
    }

    @Override
    public List<TestQuestionDto> getAllQuestionsByTopic(UUID topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));

        return toTestQuestionDtoList(testQuestionRepository.findAllByTopicAndStatus(topic, PENDING));
    }

    private void updateTestAnswer(TestAnswerDto testAnswerDto) {
        TestAnswer testAnswer = testAnswerRepository.findById(testAnswerDto.getId())
                .orElseThrow(() -> new NotFoundException(TEST_ANSWER_NOT_FOUND));

        toTestAnswerEntity(testAnswer, testAnswerDto);

        testAnswerRepository.save(testAnswer);
    }

    private void addTestAnswer(TestAnswerDto testAnswerDto, TestQuestion question) {
        TestAnswer testAnswer = toTestAnswerEntity(testAnswerDto);
        testAnswer.setQuestion(question);

        testAnswerRepository.save(testAnswer);
    }

}
