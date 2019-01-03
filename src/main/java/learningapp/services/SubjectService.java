package learningapp.services;

import java.util.UUID;

import learningapp.dtos.SubjectDto;
import learningapp.dtos.TopicDto;

public interface SubjectService {

    UUID addSubject(SubjectDto dto);

    UUID addTopicToSubject(UUID id, TopicDto topicDto);

}
