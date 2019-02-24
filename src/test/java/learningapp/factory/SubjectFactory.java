package learningapp.factory;

import java.util.Arrays;
import java.util.UUID;

import learningapp.dtos.subject.SubjectDto;

import static learningapp.factory.TopicFactory.generateTopicDto;
import static learningapp.utils.TestConstants.SUBJECT_ID;
import static learningapp.utils.TestConstants.SUBJECT_NAME;

public class SubjectFactory {

    public static SubjectDto.SubjectDtoBuilder generateSubjectDtoBuilder() {
        return SubjectDto.builder()
                .id(UUID.fromString(SUBJECT_ID))
                .name(SUBJECT_NAME)
                .topicDtos(Arrays.asList(generateTopicDto()));
    }

    public static SubjectDto generateSubjectDto() {
        return generateSubjectDtoBuilder().build();
    }

}
