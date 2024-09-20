package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;

public interface VetAppointmentTypeOfferedService {
  // Get appointments by their appointmentTypeId
  public Collection<VetAppointmentTypeOffered> getVetAppointmentTypeOfferedByAppointmentTypeID(int appointmentTypeId);
}