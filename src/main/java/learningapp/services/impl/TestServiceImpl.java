package learningapp.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.question.TestAnswerDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.TestService;

import static learningapp.mappers.test.TestAnswerMapper.toTestAnswerEntity;
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
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found!"));

        TestQuestion testQuestion = toTestQuestionEntity(testQuestionDto);

        testQuestion.setTopic(topic);
        testQuestion = testQuestionRepository.save(testQuestion);

        for (TestAnswerDto testAnswerDto : testQuestionDto.getAnswerDtos()) {
            addTestAnswer(testAnswerDto, testQuestion);
        }

        return testQuestion.getId();
    }

    private void addTestAnswer(TestAnswerDto testAnswerDto, TestQuestion question) {
        TestAnswer testAnswer = toTestAnswerEntity(testAnswerDto);
        testAnswer.setQuestion(question);

        testAnswerRepository.save(testAnswer);
    }

}
