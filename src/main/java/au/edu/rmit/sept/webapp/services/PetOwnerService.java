package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.PetOwner;

public interface PetOwnerService {
  // Get all the pet owners
  public Collection<PetOwner> getAllPetOwners();

  // Get a pet owner by their ID
  public PetOwner getPetOwnerByUserID(int userID);

  // Create a new pet owner
  public PetOwner createPetOwner(PetOwner petOwner);  
}