package learningapp.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learningapp.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {

    @Query("SELECT p.user.id from Professor p join p.subjects s join s.topics t" +
            " WHERE t.id = ?1")
    List<UUID> findAllByTopic(UUID topicId);

}
