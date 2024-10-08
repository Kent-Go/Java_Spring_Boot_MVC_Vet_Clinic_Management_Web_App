package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Clinic;

public interface ClinicService {
  // Get all the clinics
  public Collection<Clinic> getAllClinics();

  // Get clinic by clinic id
  public Clinic getClinicByClinicID(int clinicID);
}