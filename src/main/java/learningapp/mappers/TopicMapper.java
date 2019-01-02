package learningapp.mappers;

import java.util.List;
import java.util.stream.Collectors;

import learningapp.dtos.TopicDto;
import learningapp.entities.Topic;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TopicMapper {

    public static TopicDto toTopicDto(Topic entity) {
        return TopicDto.builder()
                .name(entity.getName())
                .subject(entity.getSubject())
                .build();
    }

    public Topic toTopicEntity(TopicDto dto) {
        return Topic.builder()
                .name(dto.getName())
                .subject(dto.getSubject())
                .build();
    }

    public static List<TopicDto> toTopicDtoList(List<Topic> topics) {
        return topics.stream()
                .map(TopicMapper::toTopicDto)
                .collect(Collectors.toList());
    }

    public static List<Topic> toTopicEntityLisr(List<TopicDto> topicDtos) {
        return topicDtos.stream()
                .map(TopicMapper::toTopicEntity)
                .collect(Collectors.toList());
    }

}
