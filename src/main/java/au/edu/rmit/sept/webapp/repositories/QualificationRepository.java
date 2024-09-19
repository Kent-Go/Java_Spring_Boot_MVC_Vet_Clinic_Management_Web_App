package au.edu.rmit.sept.webapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
    // Find all qualifications by vetID
    List<Qualification> findByVetID(int vetID);

    // Find a qualification by id
    Optional<Qualification> findById(int qualificationID);
}
