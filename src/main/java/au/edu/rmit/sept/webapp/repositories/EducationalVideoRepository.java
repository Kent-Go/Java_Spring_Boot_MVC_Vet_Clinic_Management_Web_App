package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;
import au.edu.rmit.sept.webapp.models.EducationalVideo;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EducationalVideoRepository extends JpaRepository<EducationalVideo, Integer> {
}
