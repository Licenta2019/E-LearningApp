package learningapp.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import learningapp.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

    List<Subject> findAllByOrderByName();

    @Query(value = "SELECT DISTINCT s.name FROM Subject s")
    List<String> getAllSubjectNames();

    Optional<Subject> findByName(String name);

}
