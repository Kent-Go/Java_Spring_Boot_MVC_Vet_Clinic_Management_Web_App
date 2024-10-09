package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import java.util.Optional;

import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;

public interface VetAppointmentTypeOfferedService {
  // Get appointment types by their appointmentTypeId
  public Collection<VetAppointmentTypeOffered> getVetAppointmentTypeOfferedByAppointmentTypeID(int appointmentTypeId);

  // Get appointment types by vet id and appointmentTypeId
  public Optional<VetAppointmentTypeOffered> getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID(int vetId, int appointmentTypeId);
}