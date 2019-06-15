package learningapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.entities.Subject;
import learningapp.entities.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {

    @Query("SELECT DISTINCT t from Test t join t.questions q " +
            "where q.topic.subject =?1")
    List<Test> getAllBySubject(Subject subject);
}
