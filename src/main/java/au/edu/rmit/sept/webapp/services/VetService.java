package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Vet;

public interface VetService {
  // Get all vets
  public Collection<Vet> getAllVets();

  // Get vet by vetID
  public Vet getVetByVetID(int vetID);

  // Get vet by userID
  public Vet getVetByUserID(int userID);

  // Create a new vet
  public Vet createVet(Vet vet);
}