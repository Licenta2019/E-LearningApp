package learningapp.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.dtos.question.TableQuestionDto;
import learningapp.entities.TestQuestion;
import learningapp.entities.TestQuestionStatus;
import learningapp.entities.Topic;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, UUID> {

    @Query(value = "SELECT tq FROM TestQuestion tq where tq.id = ?1 AND tq.topic.id = ?2")
    Optional<TestQuestion> findByIdAndTopicId(UUID id, UUID topicId);

    List<TestQuestion> findAllByTopicAndStatus(Topic topic, TestQuestionStatus status);

    @Query(value = "SELECT " +
            "new learningapp.dtos.question.TableQuestionDto(" +
            "tq.id ,tq.text, s.name," +
            " t.name, tq.author.username,tq.status, tq.updated)" +
            " FROM TestQuestion tq JOIN tq.topic t " +
            "JOIN  t.subject s " +
            "JOIN s.professors p " +
            "JOIN p.user u " +
            "WHERE u.id = ?1 and tq.status = 'PENDING'")
    List<TableQuestionDto> findAllByProfessor(UUID professorId);

    @Query(value = "SELECT " +
            "new learningapp.dtos.question.TableQuestionDto(" +
            "tq.id ,tq.text, tq.topic.subject.name," +
            "tq.topic.name, tq.author.username,tq.status, tq.updated) " +
            "FROM TestQuestion tq " +
            "WHERE tq.author.id = ?1 and tq.status = 'PENDING'")
    List<TableQuestionDto> findAllByStudent(UUID studentId);

    @Query(value = "SELECT COUNT(tq) " +
            "FROM TestQuestion tq JOIN tq.topic t " +
            "JOIN  t.subject s " +
            "JOIN s.professors p " +
            "JOIN p.user u " +
            "WHERE u.id = ?1 and tq.status = 'PENDING'")
    int getNotificationsCountForProfessor(UUID professorId);

    @Query(value = "SELECT COUNT(tq) " +
            "FROM TestQuestion tq " +
            "WHERE tq.author.id = ?1 and tq.status = 'PENDING'")
    int getNotificationsCountForStudent(UUID studentId);

}
