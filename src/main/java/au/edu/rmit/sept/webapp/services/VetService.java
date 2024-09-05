package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Vet;

public interface VetService {
  public Collection<Vet> getAllVets();
  public Vet getVetByUserID(int userID);
  public Vet createVet(Vet vet);
}