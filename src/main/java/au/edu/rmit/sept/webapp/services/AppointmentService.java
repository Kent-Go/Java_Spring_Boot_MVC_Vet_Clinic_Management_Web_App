package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Appointment;

public interface AppointmentService {
  // Get all the appointments
  public Collection<Appointment> getAllAppointments();

  // Get an appointment by their vet ID
  public Appointment getAppointmentByVetID(int vetID);

  // Get an appointment by their pet ID
  public Appointment getAppointmentByPetID(int petID);

  // Create a new appointment
  public Appointment createAppointment(Appointment appointment);
  
}