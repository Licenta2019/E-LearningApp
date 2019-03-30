package learningapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import learningapp.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
}
