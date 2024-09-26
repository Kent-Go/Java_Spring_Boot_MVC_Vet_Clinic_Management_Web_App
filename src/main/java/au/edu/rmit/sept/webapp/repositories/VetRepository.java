package au.edu.rmit.sept.webapp.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Vet;

import java.util.Collection;

@Repository
public interface VetRepository extends JpaRepository<Vet, Integer> {
    Optional<Vet> findByUserID(int userID);

    Collection<Vet> findByClinicId(int clinicID);
}
