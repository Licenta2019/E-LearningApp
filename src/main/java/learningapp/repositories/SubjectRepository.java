package learningapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learningapp.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,UUID>{


}
