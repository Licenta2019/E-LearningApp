package learningapp.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;
import learningapp.entities.Subject;
import learningapp.entities.Topic;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.SubjectService;

import static learningapp.mappers.subject.SubjectMapper.toSubjectDto;
import static learningapp.mappers.subject.SubjectMapper.toSubjectDtoList;
import static learningapp.mappers.subject.SubjectMapper.toSubjectEntity;
import static learningapp.mappers.subject.TopicMapper.toTopicEntity;
import static learningapp.mappers.subject.TopicMapper.toTopicEntityList;

@Service
public class SubjectServiceImpl implements SubjectService {

    //TODO(Paul) add exceptions for validation

    private final SubjectRepository subjectRepository;

    private final TopicRepository topicRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, TopicRepository topicRepository) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public UUID addSubject(SubjectDto dto) {
        Subject subject = subjectRepository.save(toSubjectEntity(dto));

        List<Topic> topics = toTopicEntityList(dto.getTopicDtos());
        topics.forEach(topic -> topic.setSubject(subject));

        topics = topicRepository.saveAll(topics);
        subject.setTopics(topics);

        return subjectRepository.save(subject).getId();
    }

    @Override
    @Transactional
    public UUID addTopicToSubject(UUID id, TopicDto topicDto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no such subject!"));

        Topic topic = toTopicEntity(topicDto);
        topic.setSubject(subject);

        return topicRepository.save(topic).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto getSubject(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("no such subject!"));

        return toSubjectDto(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDto> getAllSubjects() {
        return toSubjectDtoList(subjectRepository.findAllByOrderByName());
    }

    @Override
    @Transactional
    public UUID updateSubject(UUID subjectId, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("no such subject!"));

        subject.setName(subjectDto.getName());
        subjectDto.getTopicDtos().forEach(this::updateTopic);

        return subject.getId();
    }

    private void updateTopic(TopicDto topicDto) {
        Topic topic = topicRepository.findById(topicDto.getId())
                .orElseThrow(() -> new RuntimeException("no such topic!"));
        topic.setName(topicDto.getName());
    }

}
