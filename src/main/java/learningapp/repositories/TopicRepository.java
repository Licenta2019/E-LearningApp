package learningapp.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import learningapp.entities.Subject;
import learningapp.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    List<Topic> findAllBySubjectOrderByName(Subject subject);

    @Query(value = "Select t from Topic t where name like CONCAT('%', ?1, '%')")
    List<Topic> findAllContainingNumber(String number);

}
