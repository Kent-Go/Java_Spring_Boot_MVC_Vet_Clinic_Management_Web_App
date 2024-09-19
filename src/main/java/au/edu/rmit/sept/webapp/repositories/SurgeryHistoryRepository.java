package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.SurgeryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SurgeryHistoryRepository extends JpaRepository<SurgeryHistory, Integer> {
    // Get surgery history by pet ID sorted by date
    Optional<List<SurgeryHistory>> findByPetIDOrderByDateAsc(int petID);
}
