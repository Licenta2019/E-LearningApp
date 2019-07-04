package learningapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.entities.TestAnswer;
import learningapp.entities.TestQuestion;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, UUID> {

    List<TestAnswer> findByQuestion(TestQuestion testQuestion);


    @Query("select ta.id from TestAnswer ta where ta.question.id = ?1 and ta.isCorrect = 'true' ")
    List<UUID> findAllCorrectAnswersForQuestion(UUID questionId);

    void deleteAllByQuestion(TestQuestion testQuestion);
}
