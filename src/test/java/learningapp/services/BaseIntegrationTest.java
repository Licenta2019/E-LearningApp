package learningapp.services;

import org.assertj.core.api.ThrowableAssert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import learningapp.config.H2TestConfiguration;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TopicRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2TestConfiguration.class)
public class BaseIntegrationTest {

    @Autowired
    protected SubjectRepository subjectRepository;

    @Autowired
    protected TopicRepository topicRepository;

    public void assertThatThrownByError(ThrowableAssert.ThrowingCallable consumer, Class matchingException, String exceptionMessage) {
        assertThatThrownBy(consumer)
                .isInstanceOf(matchingException)
                .matches(exc -> exc.getMessage().equals(exceptionMessage));
    }

}
