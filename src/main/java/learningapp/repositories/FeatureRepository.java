package learningapp.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import learningapp.entities.Feature;

public interface FeatureRepository extends JpaRepository<Feature, UUID> {
}
