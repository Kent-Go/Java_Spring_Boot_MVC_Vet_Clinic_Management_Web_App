package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Pet;

public interface PetService {
    // Get all the pets
    public Collection<Pet> getAllPets();

    // Get a pet by their ID
    public Pet getPetByPetID(int petID);

    // Get all of a certain owner's pets
    public Collection<Pet> getPetsByPetOwnerID(int petOwnerID);

    // Create a new pet
    public Pet createPet(Pet pet);

    // Update a pet
    public Pet updatePet(Pet pet);

    // Update a pet's allergies and existing conditions by pet ID
    public Pet updatePetAllergiesAndExistingConditionsByPetID(int petID, String allergies, String existingConditions);
}