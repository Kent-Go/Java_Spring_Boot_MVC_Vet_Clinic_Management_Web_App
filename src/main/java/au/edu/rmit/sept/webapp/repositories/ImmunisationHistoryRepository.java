package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import java.util.Optional;

import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmunisationHistoryRepository extends JpaRepository<ImmunisationHistory, Integer> {
    Optional<List<ImmunisationHistory>> findByPetID(int petID);
}
