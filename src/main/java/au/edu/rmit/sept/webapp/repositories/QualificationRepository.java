package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Qualification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
    // Find all qualifications by vetID
    List<Qualification> findByVetID(int vetID);

    // Find a qualification by id
    Optional<Qualification> findById(int qualificationID);
}
