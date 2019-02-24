package learningapp.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;
import learningapp.entities.Subject;
import learningapp.entities.Topic;
import learningapp.exceptions.NotFoundException;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TopicRepository;
import learningapp.services.SubjectService;
import learningapp.validator.SubjectValidator;

import static learningapp.exceptions.ExceptionMessages.SUBJECT_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TOPIC_NOT_FOUND;
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

    private final SubjectValidator subjectValidator;

    public SubjectServiceImpl(SubjectRepository subjectRepository, TopicRepository topicRepository, SubjectValidator subjectValidator) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.subjectValidator = subjectValidator;
    }

    @Override
    @Transactional
    public UUID addSubject(SubjectDto dto) {
        subjectValidator.validateSubject(dto);

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
        Subject subject = getSubjectEntity(id);

        Topic topic = toTopicEntity(topicDto);
        topic.setSubject(subject);

        return topicRepository.save(topic).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDto getSubject(UUID id) {
        Subject subject = getSubjectEntity(id);

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
        Subject subject = getSubjectEntity(subjectId);

        subject.setName(subjectDto.getName());
        subjectDto.getTopicDtos().forEach(this::updateTopic);

        return subject.getId();
    }

    private Subject getSubjectEntity(UUID subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));
    }

    private void updateTopic(TopicDto topicDto) {
        Topic topic = topicRepository.findById(topicDto.getId())
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND));
        topic.setName(topicDto.getName());
    }

}
