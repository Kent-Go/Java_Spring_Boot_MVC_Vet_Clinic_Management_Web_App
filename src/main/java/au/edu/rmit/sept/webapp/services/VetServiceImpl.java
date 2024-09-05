package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public Vet getVetByUserID(int userID) {
    return vetRepository.findByUserID(userID).orElse(null);
  }

  @Override
  public Vet createVet(Vet vet) {
    return vetRepository.save(vet);
  }
}