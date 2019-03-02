package learningapp.services;

import java.util.Arrays;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import learningapp.config.H2TestConfiguration;
import learningapp.entities.Subject;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static learningapp.factory.SubjectFactory.generateSubject;
import static learningapp.factory.TestAnswerFactory.generateTestAnswer;
import static learningapp.factory.TestQuestionFactory.generateTestQuestion;
import static learningapp.factory.TopicFactory.generateTopic;
import static learningapp.factory.UserFactory.generateUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2TestConfiguration.class)
public class BaseIntegrationTest {

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected TopicRepository topicRepository;

    @Autowired
    protected TestQuestionRepository testQuestionRepository;

    @Autowired
    protected TestAnswerRepository testAnswerRepository;

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void fakeTest() {
    }

    public void assertThatThrownByError(ThrowableAssert.ThrowingCallable consumer, Class matchingException, String exceptionMessage) {
        assertThatThrownBy(consumer)
                .isInstanceOf(matchingException)
                .matches(exc -> exc.getMessage().equals(exceptionMessage));
    }

    public Topic createRandomSubjectWithTopic() {
        Subject subject = subjectRepository.save(generateSubject());

        Topic topic = generateTopic();

        topic.setSubject(subject);
        subject.setTopics(Arrays.asList(topic));

        return topicRepository.save(topic);
    }

    public TestQuestion createRandomTestQuestion(Topic topic) {
        TestQuestion testQuestion = generateTestQuestion();
        testQuestion.setTopic(topic);

        testQuestion = testQuestionRepository.save(testQuestion);

        TestAnswer testAnswer = generateTestAnswer();
        testAnswer.setQuestion(testQuestion);
        testQuestion.setAnswers(Arrays.asList(testAnswer));

        testAnswerRepository.save(testAnswer);

        return testQuestion;
    }

    public User createRandomUser() {
        return userRepository.save(generateUser());
    }

}
