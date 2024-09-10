package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Qualification;

public interface QualificationService {
  // Get qualification by their vet_id
  public Qualification getQualificationByVetID(int vetID);

  // Create a new qualification
  public Qualification createQualification(Qualification qualification);
}