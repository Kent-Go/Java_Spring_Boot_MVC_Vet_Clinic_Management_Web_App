package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
}
