package learningapp.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import learningapp.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndPassword(String username, String password);

}
