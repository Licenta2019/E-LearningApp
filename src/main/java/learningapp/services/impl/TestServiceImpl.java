package learningapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.dtos.test.TopicTestDto;
import learningapp.entities.Subject;
import learningapp.entities.Test;
import learningapp.entities.TestQuestion;
import learningapp.exceptions.base.NotFoundException;
import learningapp.repositories.ProfessorRepository;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TestRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.TestService;

import static learningapp.exceptions.ExceptionMessages.SUBJECT_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.handlers.SecurityContextHolderAdapter.getCurrentUser;
import static learningapp.mappers.test.TestMapper.toBaseTestDtoList;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final SubjectRepository subjectRepository;

    private final ProfessorRepository professorRepository;

    private final UserRepository userRepository;

    public TestServiceImpl(TestRepository testRepository,
                           TestQuestionRepository testQuestionRepository,
                           SubjectRepository subjectRepository,
                           ProfessorRepository professorRepository,
                           UserRepository userRepository) {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.subjectRepository = subjectRepository;
        this.professorRepository = professorRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UUID addTest(CreationTestDto CreationTestDto) {
        Test test = new Test();

        test.setName(CreationTestDto.getName());
        test.setAuthor(userRepository.findByUsername(getCurrentUser()).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND)));

        List<TestQuestion> questions = new ArrayList<>();

        CreationTestDto.getTopicTestDtoList().forEach(dto -> questions.addAll(selectQuestionsFromTopic(dto)));

        test.setQuestions(questions);

        return testRepository.save(test).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaseTestDto> getTests(UUID subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(SUBJECT_NOT_FOUND));

        return toBaseTestDtoList(testRepository.getAllBySubject(subject));
    }

    private List<TestQuestion> selectQuestionsFromTopic(TopicTestDto topicCreationTestDto) {
        return testQuestionRepository.selectRandomQuestions(topicCreationTestDto.getTopicId(), topicCreationTestDto.getQuestionsNumber());
    }

}
