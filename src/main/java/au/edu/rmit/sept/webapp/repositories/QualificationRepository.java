package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Qualification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualificationRepository extends JpaRepository<Qualification, Integer> {
    Optional<Qualification> findByVetID(int vetID);
}
