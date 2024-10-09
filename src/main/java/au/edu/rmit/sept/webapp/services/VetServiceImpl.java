package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Vet;
import au.edu.rmit.sept.webapp.repositories.VetRepository;

@Service
public class VetServiceImpl implements VetService {

  private VetRepository vetRepository;

  @Autowired
  public VetServiceImpl(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  public Collection<Vet> getAllVets() {
    return vetRepository.findAll();
  }

  @Override
  public Vet getVetByVetID(int vetID) {
    return vetRepository.findById(vetID).orElse(null);
  }

  @Override
  public Vet getVetByUserID(int userID) {
    return vetRepository.findByUserID(userID).orElse(null);
  }

  @Override
  public Vet createVet(Vet vet) {
    if (vet.getTitle() == null || vet.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Title is required");
    }
    if (vet.getLanguagesSpoken() == null || vet.getLanguagesSpoken().isEmpty()) {
      throw new IllegalArgumentException("Languages spoken is required");
    }
    if (vet.getSelfDescription() == null || vet.getSelfDescription().isEmpty()) {
      throw new IllegalArgumentException("Self description is required");
    }
    // Add any other field validation as required

    return vetRepository.save(vet);
  }

  @Override
  public Collection<Vet> getVetsByClinicID(int clinicID) {
    return vetRepository.findByClinicId(clinicID);
  }
}