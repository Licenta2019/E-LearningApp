package learningapp.factory;

import java.util.UUID;

import learningapp.dtos.subject.TopicDto;
import learningapp.entities.Topic;

import static learningapp.utils.TestConstants.TOPIC1_ID;
import static learningapp.utils.TestConstants.TOPIC1_NAME;

public class TopicFactory {

    public static TopicDto.TopicDtoBuilder generateTopicDtoBuilder() {
        return TopicDto.builder()
                .id(UUID.fromString(TOPIC1_ID))
                .name(TOPIC1_NAME);
    }

    public static TopicDto generateTopicDto() {
        return generateTopicDtoBuilder().build();
    }

    public static Topic generateTopic() {
        return Topic.builder()
                .name(TOPIC1_NAME)
                .build();
    }

}
