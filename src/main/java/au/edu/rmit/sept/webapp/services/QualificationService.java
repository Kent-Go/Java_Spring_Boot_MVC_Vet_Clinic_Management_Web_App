package au.edu.rmit.sept.webapp.services;

import java.util.List;
import au.edu.rmit.sept.webapp.models.Qualification;

public interface QualificationService {
  // Get qualification by their vet_id
  public List<Qualification> getQualificationsByVetID(int vetID);

  // Create a new qualification
  public Qualification createQualification(Qualification qualification);
}