package learningapp.factory;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import learningapp.dtos.subject.SubjectDto;
import learningapp.entities.Subject;

import static learningapp.factory.TopicFactory.generateTopicDto;
import static learningapp.utils.TestConstants.SUBJECT_ID;
import static learningapp.utils.TestConstants.SUBJECT_NAME;

public class SubjectFactory {

    private static Random random = new Random();

    public static SubjectDto.SubjectDtoBuilder generateSubjectDtoBuilder() {
        return SubjectDto.builder()
                .id(UUID.fromString(SUBJECT_ID))
                .name(SUBJECT_NAME)
                .topicDtos(Arrays.asList(generateTopicDto()));
    }

    public static SubjectDto generateSubjectDto() {
        return generateSubjectDtoBuilder().build();
    }

    public static Subject generateSubject() {
        return Subject.builder()
                .name(generateRandomSubjectName()).build();
    }

    public static String generateRandomSubjectName() {
        return "SUBJECT" + random.nextInt();
    }

}
