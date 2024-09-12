package au.edu.rmit.sept.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {
}
