package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmunisationHistoryRepository extends JpaRepository<ImmunisationHistory, Integer> {

}
