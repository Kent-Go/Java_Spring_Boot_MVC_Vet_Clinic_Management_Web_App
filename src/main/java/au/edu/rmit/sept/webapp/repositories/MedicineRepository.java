package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}
