package learningapp.handlers;

import learningapp.repositories.TestAnswerRepository;
import learningapp.repositories.TestQuestionRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateQuestionHandler {

    private TestQuestionRepository testQuestionRepository;

    private TestAnswerRepository testAnswerRepository;

    public UpdateQuestionHandler(TestQuestionRepository testQuestionRepository, TestAnswerRepository testAnswerRepository) {
        this.testQuestionRepository = testQuestionRepository;
        this.testAnswerRepository = testAnswerRepository;
    }

    public void updateQuestion() {

    }

}
