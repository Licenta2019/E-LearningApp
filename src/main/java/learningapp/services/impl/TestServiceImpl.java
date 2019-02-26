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
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.TestService;

import static learningapp.exceptions.ExceptionMessages.TEST_ANSWER_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_QUESTION_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntity;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionDtoList;
import static learningapp.mappers.test.TestQuestionMapper.toTestQuestionEntity;

@Service
public class TestServiceImpl implements TestService {

    private final TopicRepository topicRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final TestAnswerRepository testAnswerRepository;

    public TestServiceImpl(TopicRepository topicRepository,
                           TestQuestionRepository testQuestionRepository,
                           TestAnswerRepository testAnswerRepository) {
        this.topicRepository = topicRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
    }

    @Override
    @Transactional
    public UUID addTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));

        TestQuestion testQuestion = toTestQuestionEntity(testQuestionDto);

        testQuestion.setTopic(topic);
        testQuestion = testQuestionRepository.save(testQuestion);

        for (TestAnswerDto testAnswerDto : testQuestionDto.getAnswerDtos()) {
            addTestAnswer(testAnswerDto, testQuestion);
        }

        return testQuestion.getId();
    }

    @Override
    @Transactional
    public UUID updateTestQuestion(UUID topicId, TestQuestionDto testQuestionDto) {
        //TODO(Paul) see if we can bind validated to some statuses
        //TODO(fix mapper for update)

        TestQuestion testQuestion = testQuestionRepository.findByIdAndTopicId(testQuestionDto.getId(), topicId)
                .orElseThrow(() -> new NotFoundException(TEST_QUESTION_NOT_FOUND));

        testQuestion.setText(testQuestionDto.getQuestionText());

        testQuestionDto.getAnswerDtos().forEach(this::updateTestAnswer);

        testQuestion.setWasValidated(true);

        return testQuestionRepository.save(testQuestion).getId();
    }

    @Override
    public List<TestQuestionDto> getAllQuestionsByTopic(UUID topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));

        return toTestQuestionDtoList(testQuestionRepository.findAllByTopic(topic));
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
