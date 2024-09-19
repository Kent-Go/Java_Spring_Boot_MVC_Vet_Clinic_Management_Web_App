package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import au.edu.rmit.sept.webapp.models.ImmunisationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImmunisationHistoryRepository extends JpaRepository<ImmunisationHistory, Integer> {
    Optional<List<ImmunisationHistory>> findByPetIDOrderByDateAsc(int petID);
}
