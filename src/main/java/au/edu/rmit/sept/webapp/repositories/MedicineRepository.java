package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}
