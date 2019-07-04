package learningapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;
import learningapp.entities.Subject;
import learningapp.entities.Topic;
import learningapp.exceptions.base.DuplicateEntityException;
import learningapp.exceptions.base.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static learningapp.exceptions.ExceptionMessages.DUPLICATE_SUBJECT;
import static learningapp.exceptions.ExceptionMessages.SUBJECT_NOT_FOUND;
import static learningapp.factory.SubjectFactory.generateSubjectDto;
import static learningapp.factory.SubjectFactory.generateSubjectDtoBuilder;
import static learningapp.factory.TopicFactory.generateTopicDto;
import static learningapp.factory.TopicFactory.generateTopicDtoBuilder;
import static learningapp.mappers.GeneralMapper.uuidFromString;
import static learningapp.utils.TestConstants.INEXISTENT_ID;
import static learningapp.utils.TestConstants.SUBJECT_A_NAME;
import static learningapp.utils.TestConstants.SUBJECT_B_NAME;
import static learningapp.utils.TestConstants.SUBJECT_NAME_2;
import static learningapp.utils.TestConstants.SUBJECT_NOT_UNIQUE;
import static learningapp.utils.TestConstants.SUBJECT_UNIQUE_NAME;
import static learningapp.utils.TestConstants.UPDATED_TOPIC_NAME;

public class SubjectIT extends BaseIntegrationTest {

    @Autowired
    public SubjectService subjectService;

    @Test
    public void givenValidSubjectDto_whenAddSubject_thenUuidReturned() {

        //given
        SubjectDto subjectDto = generateSubjectDtoBuilder()
                .name(SUBJECT_NAME_2)
                .build();

        //when
        UUID subjectId = subjectService.addSubject(subjectDto);

        //then
        assertNotNull(subjectId);

        Subject subject = subjectRepository.findById(subjectId).get();

        assertEquals(subjectDto.getName(), subject.getName());

        List<Topic> topics = topicRepository.findAllBySubjectOrderByName(subject);

        assertNotNull(topics);
        assertEquals(topics.size(), subjectDto.getTopicDtos().size());
        assertTopicsEquals(topics, subjectDto.getTopicDtos());
    }

    @Test
    public void givenSubjectDtoWithExistentSubjectName_whenAddSubject_thenExceptionIsThrown() {

        //given
        SubjectDto subjectDto = generateSubjectDto();
        subjectDto.setName(SUBJECT_NOT_UNIQUE);

        subjectService.addSubject(subjectDto);

        //when
        assertThatThrownByError(() -> subjectService.addSubject(subjectDto),

                //then
                DuplicateEntityException.class, DUPLICATE_SUBJECT);
    }


    @Test
    public void givenExistentSubjectId_whenGetOneSubject_thenSubjectDtoIsReturned() {

        //given
        SubjectDto subjectDto = generateSubjectDtoBuilder()
                .name(SUBJECT_UNIQUE_NAME)
                .build();

        UUID subjectId = subjectService.addSubject(subjectDto);

        //when
        SubjectDto subjectDto2 = subjectService.getSubject(subjectId);

        //then
        assertNotNull(subjectDto2);
        assertEquals(subjectDto.getName(), subjectDto2.getName());
        assertTopicDtosEquals(subjectDto.getTopicDtos(), subjectDto2.getTopicDtos());
    }

    @Test
    public void givenInexistentSubjectId_whenGetOneSubject_thenSubjectDtoIsReturned() {

        //given

        //when
        assertThatThrownByError(() -> subjectService.getSubject(uuidFromString(INEXISTENT_ID)),

                //then
                NotFoundException.class, SUBJECT_NOT_FOUND);
    }

    @Test
    public void givenTwoExistentSubject_whenGetAllSubjects_thenSubjectDtoListIsReturned() {

        //given
        SubjectDto subjectDto = generateSubjectDtoBuilder()
                .name(SUBJECT_B_NAME)
                .build();
        subjectService.addSubject(subjectDto);

        SubjectDto subjectDto2 = generateSubjectDtoBuilder()
                .name(SUBJECT_A_NAME)
                .id(UUID.randomUUID())
                .build();
        subjectService.addSubject(subjectDto2);

        //when
        List<SubjectDto> subjectDtos = subjectService.getAllSubjects();

        //then
        assertNotNull(subjectDtos);

        List<String> subjectNames = subjectRepository.getAllSubjectNames();
        assertTrue(subjectNames.containsAll(Arrays.asList(SUBJECT_A_NAME, SUBJECT_B_NAME)));
    }

    @Test
    public void givenValidTopicDto_whenAddTopicToSubject_thenUuidReturned() {

        //given
        TopicDto topicDto = generateTopicDto();

        SubjectDto subjectDto = generateSubjectDtoBuilder()
                .name(SUBJECT_B_NAME + "11")
                .build();
        UUID subjectId = subjectService.addSubject(subjectDto);

        //when
        UUID topicId = subjectService.addTopicToSubject(subjectId, topicDto);

        //then
        Topic topic = topicRepository.findById(topicId).get();

        assertEquals(topic.getName(), topicDto.getName());
    }

    @Test
    public void givenInexistentSubjectId_whenAppTopicToSubject_thenExceptionIsThrown() {

        //given
        TopicDto topicDto = generateTopicDto();

        //when
        assertThatThrownByError(() -> subjectService.addTopicToSubject(uuidFromString(INEXISTENT_ID), topicDto),

                //then
                NotFoundException.class, SUBJECT_NOT_FOUND);
    }

    @Test
    public void givenValidSubjectDto_whenUpdateSubject_thenUuidReturned() {

        //given
        UUID subjectId = subjectService.addSubject(generateSubjectDtoBuilder()
                .name(SUBJECT_B_NAME + "111")
                .build());

        UUID topicId = topicRepository.findAllBySubjectOrderByName(subjectRepository.findById(subjectId).get()).get(0).getId();

        SubjectDto updatedSubjectDto = generateSubjectDtoBuilder()
                .name(SUBJECT_A_NAME + "111")
                .topicDtos(Arrays.asList(generateTopicDtoBuilder()
                        .id(topicId)
                        .name(UPDATED_TOPIC_NAME)
                        .build()))
                .build();

        //when
        subjectService.updateSubject(subjectId, updatedSubjectDto);

        //then
        Subject subject = subjectRepository.findById(subjectId).get();

        assertEquals(subject.getName(), updatedSubjectDto.getName());

        List<Topic> topics = topicRepository.findAllBySubjectOrderByName(subject);

        assertNotNull(topics);
        assertTopicsEquals(topics, updatedSubjectDto.getTopicDtos());
    }

    @Test
    public void givenInexistentSubjectId_whenUpdateSubject_thenExceptionIsThrown() {

        //given
        SubjectDto subjectDto = generateSubjectDto();

        //when
        assertThatThrownByError(() -> subjectService.updateSubject(uuidFromString(INEXISTENT_ID), subjectDto),

                //then
                NotFoundException.class, SUBJECT_NOT_FOUND);
    }

    private void assertTopicsEquals(List<Topic> topics, List<TopicDto> topicDtos) {
        for (int i = 0; i < topics.size(); ++i) {
            Topic topic = topics.get(i);
            TopicDto topicDto = topicDtos.get(i);

            assertEquals(topic.getName(), topicDto.getName());
        }
    }

    private void assertTopicDtosEquals(List<TopicDto> topicDtos1, List<TopicDto> topicDtos2) {
        for (int i = 0; i < topicDtos1.size(); ++i) {
            TopicDto topicDto1 = topicDtos1.get(i);
            TopicDto topicDto2 = topicDtos2.get(i);

            assertEquals(topicDto1.getName(), topicDto2.getName());
        }
    }
}
