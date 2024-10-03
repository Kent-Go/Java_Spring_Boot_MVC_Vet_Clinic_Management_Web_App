package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;

public interface ClinicAppointmentTypePriceService {
  // Get ClinicAppointmentTypePrice by clinic id and appointment type id
  public ClinicAppointmentTypePrice getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(int clinicID, int appointmentTypeID);
}