package learningapp.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import learningapp.config.H2TestConfiguration;
import learningapp.entities.Subject;
import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;
import learningapp.entities.TestQuestionStatus;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.entities.notification.Notification;
import learningapp.entities.notification.NotificationUser;
import learningapp.entities.notification.NotificationUserId;
import learningapp.repositories.NotificationRepository;
import learningapp.repositories.NotificationUserRepository;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TopicRepository;
import learningapp.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static learningapp.entities.notification.NotificationType.INFO;
import static learningapp.factory.SubjectFactory.generateSubject;
import static learningapp.factory.TestAnswerFactory.generateTestAnswer;
import static learningapp.factory.TestQuestionFactory.generateTestQuestion;
import static learningapp.factory.TopicFactory.generateTopic;
import static learningapp.factory.UserFactory.generateUser;
import static learningapp.factory.UserFactory.generateUserBuilder;

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

    @Autowired
    protected NotificationRepository notificationRepository;

    @Autowired
    protected NotificationUserRepository notificationUserRepository;

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

    @Transactional
    public TestQuestion createRandomTestQuestion(Topic topic, User author, TestQuestionStatus status) {
        TestQuestion testQuestion = generateTestQuestion();

        testQuestion.setTopic(topic);
        testQuestion.setAuthor(author);
        testQuestion.setStatus(status);

        testQuestion = testQuestionRepository.save(testQuestion);

        TestAnswer testAnswer = generateTestAnswer();
        testAnswer.setQuestion(testQuestion);
        testQuestion.setAnswers(Arrays.asList(testAnswer));

        testAnswerRepository.save(testAnswer);

        return testQuestionRepository.save(testQuestion);
    }

    public User findOrCreateUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.orElseGet(() -> userRepository.save(generateUserBuilder()
                .username(username)
                .build()));
    }

    public User createRandomUser() {
        return userRepository.save(generateUser());
    }

    public void createRandomNotification(User user) {

        Notification notification = new Notification();
        notification.setMessage("Message");
        notification.setType(INFO);
        notificationRepository.save(notification);

        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotificationUserId(new NotificationUserId(user.getId(), notification.getId()));
        notificationUser.setUser(user);
        notificationUser.setNotification(notification);
        notificationUserRepository.save(notificationUser);

        notification.setNotificationUsers(Collections.singletonList(notificationUser));
    }

}
