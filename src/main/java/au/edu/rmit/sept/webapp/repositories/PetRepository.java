package au.edu.rmit.sept.webapp.repositories;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.edu.rmit.sept.webapp.models.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    Collection<Pet> findAllByPetOwnerID(int ownerID);
}