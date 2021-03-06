package learningapp.services.impl;

import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestAnswerDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.*;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.StudentRepository;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static learningapp.entities.TestQuestionStatus.*;
import static learningapp.exceptions.ExceptionMessages.*;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntity;
import static learningapp.mappers.test.TestQuestionMapper.*;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final TopicRepository topicRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final TestAnswerRepository testAnswerRepository;

    private final StudentRepository studentRepository;

    public QuestionServiceImpl(TopicRepository topicRepository,
                               TestQuestionRepository testQuestionRepository,
                               TestAnswerRepository testAnswerRepository,
                               StudentRepository studentRepository) {
        this.topicRepository = topicRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
        this.studentRepository = studentRepository;
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
        Student student = studentRepository.findById(testQuestionDto.getStudentId())
                .orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));

        TestQuestion testQuestion = toTestQuestionEntity(testQuestionDto);

        testQuestion.setTopic(topic);
        testQuestion.setStudent(student);

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
    @Transactional
    public UUID updateTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        TestQuestion testQuestion = updateTestQuestionEntity(testQuestionDto, REQUESTED_CHANGES);

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
        updateTestQuestionEntity(testQuestionDto, VALIDATED);
    }

    private TestQuestion updateTestQuestionEntity(TestQuestionDto testQuestionDto, TestQuestionStatus newStatus) {
        TestQuestion testQuestion = getTestQuestionEntity(testQuestionDto.getId());

        Topic topic = getTopicEntity(testQuestionDto.getTopicId());

        testQuestionDto.getAnswerDtos().forEach(this::updateTestAnswer);

        toTestQuestionEntity(testQuestion, testQuestionDto, newStatus);
        testQuestion.setTopic(topic);

        return testQuestionRepository.save(testQuestion);
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
