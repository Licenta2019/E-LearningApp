package learningapp.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learningapp.dtos.SubjectDto;
import learningapp.dtos.TopicDto;
import learningapp.mappers.SubjectMapper;
import learningapp.repositories.SubjectRepository;
import learningapp.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public UUID addSubject(SubjectDto dto) {
        return subjectRepository.save(SubjectMapper.toSubjectEntity(dto)).getId();
    }

    @Override
    public UUID addTopicToSubject(UUID id, TopicDto topicDto) {
        return null;
    }

}
