package au.edu.rmit.sept.webapp.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {
    Optional<PetOwner> findByUserID(int userID);
}
