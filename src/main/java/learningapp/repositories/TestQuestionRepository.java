package learningapp.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.entities.TestQuestion;
import learningapp.entities.Topic;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, UUID> {

    @Query(value = "SELECT tq FROM TestQuestion tq where tq.id = ?1 AND tq.topic.id = ?2")
    Optional<TestQuestion> findByIdAndTopicId(UUID id, UUID topicId);

    List<TestQuestion> findAllByTopic(Topic topic);
}
