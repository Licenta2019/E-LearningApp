package learningapp.mappers;

import learningapp.dtos.SubjectDto;
import learningapp.entities.Subject;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SubjectMapper {

    public static SubjectDto toSubjectDto(Subject subject) {
        return SubjectDto.builder()
                .name(subject.getName())
                .topicDtos(TopicMapper.toTopicDtoList(subject.getTopics()))
                .build();
    }

    public static Subject toSubjectEntity(SubjectDto dto) {
        return Subject.builder()
                .name(dto.getName())
                .topics(TopicMapper.toTopicEntityLisr(dto.getTopicDtos()))
                .build();
    }

}
