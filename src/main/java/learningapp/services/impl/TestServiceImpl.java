package learningapp.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.dtos.test.TestDto;
import learningapp.dtos.test.TopicTestDto;
import learningapp.entities.Subject;
import learningapp.entities.Test;
import learningapp.entities.TestDifficulty;
import learningapp.entities.TestQuestion;
import learningapp.entities.User;
import learningapp.exceptions.base.NotFoundException;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TestRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.TestService;

import static learningapp.exceptions.ExceptionMessages.SUBJECT_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.handlers.SecurityContextHolderAdapter.getCurrentUser;
import static learningapp.mappers.test.TestMapper.toBaseTestDtoList;
import static learningapp.mappers.test.TestMapper.toTestDto;
import static learningapp.mappers.test.TestMapper.toTestEntity;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final SubjectRepository subjectRepository;

    private final UserRepository userRepository;

    public TestServiceImpl(TestRepository testRepository,
                           TestQuestionRepository testQuestionRepository,
                           SubjectRepository subjectRepository,
                           UserRepository userRepository) {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UUID addTest(CreationTestDto creationTestDto) {
        User user = userRepository.findByUsername(getCurrentUser()).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND));

        Test test = toTestEntity(creationTestDto.getName(), user);
        test.setName(creationTestDto.getName());

        List<TestQuestion> questions = new ArrayList<>();

        creationTestDto.getTopicTestDtoList().forEach(dto -> questions.addAll(selectQuestionsFromTopic(dto)));

        test.setQuestions(questions);
        test.setTestDifficulty(TestDifficulty.getAssociatedDificulty(getDifficulty(test)));

        return testRepository.save(test).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaseTestDto> getTests(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        return toBaseTestDtoList(testRepository.getAllBySubject(subject));
    }

    @Override
    public List<TestDifficulty> getTestDifficulties() {
        return Arrays.asList(TestDifficulty.values());
    }

    @Override
    @Transactional(readOnly = true)
    public TestDto getTest(UUID id) {
        Test test = testRepository.findById(id).orElseThrow(() -> new NotFoundException(TEST_NOT_FOUND));

        return toTestDto(test);
    }

    private List<TestQuestion> selectQuestionsFromTopic(TopicTestDto topicCreationTestDto) {
        return testQuestionRepository.selectQuestionsHavingDifficulty(
                topicCreationTestDto.getTopicId(), topicCreationTestDto.getQuestionsNumber(),
                topicCreationTestDto.getDifficulty().getMinDifficulty(), topicCreationTestDto.getDifficulty().getMaxDifficulty());
    }

    private double getDifficulty(Test test) {
        return test.getQuestions().stream()
                .mapToDouble(TestQuestion::getDifficulty)
                .average()
                .orElse(1.0);
    }

}
