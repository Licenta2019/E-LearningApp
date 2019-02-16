package learningapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learningapp.entities.TestQuestion;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion,UUID> {
}
