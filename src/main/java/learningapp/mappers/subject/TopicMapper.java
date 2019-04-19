package learningapp.mappers.subject;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.subject.TopicDto;
import learningapp.entities.Topic;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TopicMapper {

    public static TopicDto toTopicDto(Topic entity) {
        return TopicDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Topic toTopicEntity(TopicDto dto) {
        return Topic.builder()
                .name(dto.getName())
                .build();
    }

    public static List<TopicDto> toTopicDtoList(List<Topic> topics) {
        return topics.stream()
                .map(TopicMapper::toTopicDto)
                .collect(Collectors.toList());
    }

    public static List<Topic> toTopicEntityList(List<TopicDto> topicDtos) {
        return topicDtos.stream()
                .map(TopicMapper::toTopicEntity)
                .collect(Collectors.toList());
    }

}
