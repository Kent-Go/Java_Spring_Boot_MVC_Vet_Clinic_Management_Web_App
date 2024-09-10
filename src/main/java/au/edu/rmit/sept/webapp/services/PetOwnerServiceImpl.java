package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.PetOwner;
import au.edu.rmit.sept.webapp.repositories.PetOwnerRepository;
import jakarta.transaction.Transactional;

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
  public PetOwner getPetOwnerByUserID(int userID) {
    return petOwnerRepository.findById(userID).orElseThrow(() -> new RuntimeException("Pet Owner not found"));
  }

  @Transactional
  public PetOwner createPetOwner(PetOwner petOwner) {
    return petOwnerRepository.save(petOwner);
  }
}