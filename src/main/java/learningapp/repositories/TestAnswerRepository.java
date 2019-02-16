package learningapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learningapp.entities.TestAnswer;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, UUID> {
}
