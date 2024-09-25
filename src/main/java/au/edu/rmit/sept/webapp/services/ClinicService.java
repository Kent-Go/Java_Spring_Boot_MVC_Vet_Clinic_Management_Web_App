package au.edu.rmit.sept.webapp.services;

import java.time.LocalDate;
import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Clinic;

public interface ClinicService {
  // Get all the clinics
  public Collection<Clinic> getAllClinics();
}