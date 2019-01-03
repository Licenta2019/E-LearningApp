package learningapp.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learningapp.dtos.SubjectDto;
import learningapp.dtos.TopicDto;
import learningapp.entities.Subject;
import learningapp.entities.Topic;
import learningapp.mappers.SubjectMapper;
import learningapp.mappers.TopicMapper;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    //TODO(Paul) add exceptions for validation

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public UUID addSubject(SubjectDto dto) {
        return subjectRepository.save(SubjectMapper.toSubjectEntity(dto)).getId();
    }

    @Override
    public UUID addTopicToSubject(UUID id, TopicDto topicDto) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("no such subject!"));

        Topic topic = TopicMapper.toTopicEntity(topicDto);
        topic.setSubject(subject);

        return topicRepository.save(topic).getId();
    }

}
