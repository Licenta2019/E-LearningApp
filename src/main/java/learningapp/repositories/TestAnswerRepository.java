package learningapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, UUID> {

    List<TestAnswer> findByQuestion(TestQuestion testQuestion);

    void deleteAllByQuestion(TestQuestion testQuestion);
}
