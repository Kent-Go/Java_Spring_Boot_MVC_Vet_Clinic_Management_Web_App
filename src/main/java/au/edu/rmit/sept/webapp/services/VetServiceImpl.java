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
    return vetRepository.save(vet);
  }
}