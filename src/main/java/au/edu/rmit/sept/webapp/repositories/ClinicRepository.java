package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
}
