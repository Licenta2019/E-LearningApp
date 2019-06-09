package learningapp.services;

import java.util.List;
import java.util.UUID;

import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;

public interface SubjectService {

    /**
     * Method that saves a subject via a specific subject dto.
     *
     * @param subjectDto - subject's data
     * @return
     */
    UUID addSubject(SubjectDto subjectDto);

    /**
     * Method that saves a topic to a subject via a specific topic dto.
     *
     * @param id       - corellated subject's id
     * @param topicDto - topic's data
     * @return
     */
    UUID addTopicToSubject(UUID id, TopicDto topicDto);

    /**
     * Retrieve a subject by its id.
     *
     * @param id - the subject's id.
     * @return
     */
    SubjectDto getSubject(UUID id);

    /**
     * Return a list with all saved subjects in dto format.
     *
     * @return
     */
    List<SubjectDto> getAllSubjects();

    /**
     * Update a subject via a subject dto.
     *
     * @param subjectId  - id of the subject to be updated
     * @param subjectDto - the new data for the subject
     * @return
     */
    UUID updateSubject(UUID subjectId, SubjectDto subjectDto);

}
