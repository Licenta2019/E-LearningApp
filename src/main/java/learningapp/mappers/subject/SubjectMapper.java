package learningapp.mappers.subject;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.subject.SubjectDto;
import learningapp.entities.Subject;
import lombok.experimental.UtilityClass;

import static learningapp.mappers.subject.TopicMapper.toTopicDtoList;

@UtilityClass
public class SubjectMapper {

    public static SubjectDto toSubjectDto(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .topicDtos(toTopicDtoList(subject.getTopics()))
                .build();
    }

    public static Subject toSubjectEntity(SubjectDto dto) {
        return Subject.builder()
                .name(dto.getName())
                .build();
    }

    public static List<SubjectDto> toSubjectDtoList(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectMapper::toSubjectDto)
                .collect(Collectors.toList());
    }

}
