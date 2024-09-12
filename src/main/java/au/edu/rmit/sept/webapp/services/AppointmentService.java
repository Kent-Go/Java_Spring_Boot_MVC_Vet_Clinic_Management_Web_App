package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Appointment;

import java.util.Date;

public interface AppointmentService {
  // Get all the appointments
  public Collection<Appointment> getAllAppointments();

  // Get an appointment by their vet ID
  public Collection<Appointment> getAppointmentByVetID(int vetID);

  // Get an appointment by their pet ID
  public Collection<Appointment> getAppointmentByPetID(int petID);

  // Create a new appointment
  public Appointment createAppointment(Appointment appointment);

  // Get appointments by VetID and Date
  public Collection<Appointment> getAppointmentsByVetIDAndDate(int vetID, Date date);
  
}