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
    return petOwnerRepository.save(petOwner);
  }

  @Override
  public PetOwner getPetOwnerByUserID(int userID) {
    return petOwnerRepository.findByUserID(userID);
  }
}