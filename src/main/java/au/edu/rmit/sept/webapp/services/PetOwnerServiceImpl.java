package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.repositories.PetOwnerRepository;

@Service
public class PetOwnerServiceImpl implements PetOwnerService {

  private PetOwnerRepository petOwnerRepository;

  @Autowired
  public PetOwnerServiceImpl(PetOwnerRepository petOwnerRepository) {
    this.petOwnerRepository = petOwnerRepository;
  }

  @Override
  public Collection<PetOwner> getAllPetOwners() {
    return petOwnerRepository.findAll();
  }

  @Override
  public PetOwner getPetOwnerByPetOwnerID(int petOwnerID) {
    return petOwnerRepository.findById(petOwnerID).orElseThrow(() -> new RuntimeException("Pet Owner not found"));
  }

  @Override
  public PetOwner createPetOwner(PetOwner petOwner) {
    // Check for null values or invalid data
    if (petOwner.getUserID() <= 0) {
      throw new IllegalArgumentException("User ID cannot be null or zero");
    }

    // Additional validations can go here if necessary

    return petOwnerRepository.save(petOwner);
  }


  @Override
  public PetOwner getPetOwnerByUserID(int userID) {
    return petOwnerRepository.findByUserID(userID).orElseThrow(() -> new RuntimeException("Pet Owner not found"));
  }
}