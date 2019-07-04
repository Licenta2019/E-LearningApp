package learningapp.services.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.DocumentException;

import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.dtos.test.GradeTestDto;
import learningapp.dtos.test.TakeTestDto;
import learningapp.dtos.test.TestDto;
import learningapp.dtos.test.TopicTestDto;
import learningapp.entities.Subject;
import learningapp.entities.Test;
import learningapp.entities.TestDifficulty;
import learningapp.entities.TestQuestion;
import learningapp.entities.User;
import learningapp.exceptions.base.FileException;
import learningapp.exceptions.base.NotFoundException;
import learningapp.handlers.PdfExporter;
import learningapp.repositories.FeatureRepository;
import learningapp.repositories.SubjectRepository;
import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import learningapp.repositories.TestRepository;
import learningapp.repositories.UserRepository;
import learningapp.services.TestService;
import lombok.extern.slf4j.Slf4j;

import static learningapp.exceptions.ExceptionMessages.SUBJECT_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.TEST_NOT_FOUND;
import static learningapp.exceptions.ExceptionMessages.USER_NOT_FOUND;
import static learningapp.handlers.PdfExporter.exportToPdf;
import static learningapp.handlers.SecurityContextHolderAdapter.getCurrentUser;
import static learningapp.mappers.test.TestMapper.toBaseTestDtoList;
import static learningapp.mappers.test.TestMapper.toFeatureEntity;
import static learningapp.mappers.test.TestMapper.toTestDto;
import static learningapp.mappers.test.TestMapper.toTestEntity;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final TestQuestionRepository testQuestionRepository;

    private final TestAnswerRepository testAnswerRepository;

    private final SubjectRepository subjectRepository;

    private final UserRepository userRepository;

    private final FeatureRepository featureRepository;

    public TestServiceImpl(TestRepository testRepository,
                           TestQuestionRepository testQuestionRepository,
                           TestAnswerRepository testAnswerRepository,
                           SubjectRepository subjectRepository,
                           UserRepository userRepository, FeatureRepository featureRepository, PdfExporter pdfExporter) {
        this.testRepository = testRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.featureRepository = featureRepository;
    }

    @Override
    @Transactional
    public UUID addTest(CreationTestDto creationTestDto) {
        User user = userRepository.findAll().get(0);

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

    @Override
    @Transactional
    public double gradeTest(GradeTestDto gradeTestDto) {

        Test test = testRepository.findById(gradeTestDto.getTestId())
                .orElseThrow(() -> new NotFoundException(TEST_NOT_FOUND));

        long noCorrectAnswers = gradeTestDto.getQuestions().stream()
                .filter(this::checkCorrectQuestion)
                .count();

        double grade = computeTestGrade(noCorrectAnswers, gradeTestDto.getQuestions().size());

        User user = userRepository.findByUsername(getCurrentUser())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        saveTestResult(user, test, grade);
        return grade;
    }

    @Override
    @Transactional(readOnly = true)
    public String exportTest(UUID id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(TEST_NOT_FOUND));

        try {
            return exportToPdf(test);
        } catch (FileNotFoundException | DocumentException e) {
            throw new FileException(e.getMessage());
        }
    }

    private void saveTestResult(User student, Test test, double grade) {
        featureRepository.save(toFeatureEntity(student, test, grade));
    }

    private double computeTestGrade(long noCorrectAnswers, long noQuestions) {
        return noCorrectAnswers * 10 / noQuestions;
    }

    private boolean checkCorrectQuestion(TakeTestDto dto) {
        List<UUID> testAnswers = testAnswerRepository.findAllCorrectAnswersForQuestion(dto.getQuestionId());

        return testAnswers.containsAll(dto.getAnswers()) && testAnswers.size() == dto.getAnswers().size();
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
