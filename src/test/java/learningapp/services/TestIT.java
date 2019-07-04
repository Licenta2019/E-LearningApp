package learningapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.dtos.test.TestDto;
import learningapp.dtos.test.TopicTestDto;
import learningapp.entities.TestDifficulty;
import learningapp.entities.Topic;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static learningapp.exceptions.ExceptionMessages.TEST_NOT_FOUND;
import static learningapp.utils.TestConstants.USER_NAME;

public class TestIT extends BaseIntegrationTest {

    @Autowired
    private TestService testService;

    private CreationTestDto generateCreationTestDto(List<TopicTestDto> testDtoList) {
        return CreationTestDto.builder()
                .name("test")
                .topicTestDtoList(testDtoList)
                .build();
    }

    private TopicTestDto generateTopicTestDto(UUID id, int number) {
        return TopicTestDto.builder()
                .questionsNumber(number)
                .topicId(id)
                .difficulty(TestDifficulty.RANDOM)
                .build();
    }

    @Test
    @Ignore
    public void givenValidCreationTestDto_whenSaveTest_thenOk() {
        Topic topic = createRandomSubjectWithTopic();
        findOrCreateUser(USER_NAME);

        CreationTestDto creationTestDto = generateCreationTestDto(
                Arrays.asList(generateTopicTestDto(topic.getId(), 2)));

        UUID id = testService.addTest(creationTestDto);
        assertNotNull(id);
    }

    @Test
    public void givenTwoTests_whenRetrievingTests_thenOk() {
        Topic topic = createRandomSubjectWithTopic();
        Topic topic2 = createRandomSubjectWithTopic();

        User user = findOrCreateUser(USER_NAME);

        createRandomTest(topic, user);
        createRandomTest(topic2, user);

        List<BaseTestDto> baseTestDtos = testService.getTests(topic.getSubject().getId());

        assertNotNull(baseTestDtos);
        assertEquals(1, baseTestDtos.size());
    }

    @Test
    public void givenInexistentUUID_whenGetTest_thenExceptionIsThrown() {

        assertThatThrownByError(() -> testService.getTest(UUID.randomUUID()),
                NotFoundException.class,
                TEST_NOT_FOUND
        );
    }

    @Test
    public void givenExistentTestId_whenGetTest_thenTestDtoReturned() {
        User user = findOrCreateUser(USER_NAME);
        learningapp.entities.Test test = createRandomTest(createRandomSubjectWithTopic(), user);

        TestDto testDto = testService.getTest(test.getId());

        assertNotNull(testDto);

        assertNotNull(testDto.getBaseTestDto());

        assertNotNull(testDto.getTestQuestionDtoList());
    }

}
