package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    Collection<Pet> findAllByPetOwnerID(int ownerID);
}