package au.edu.rmit.sept.webapp.services;

import java.time.LocalDate;
import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Appointment;

public interface AppointmentService {
  // Get all the appointments
  public Collection<Appointment> getAllAppointments();

  // Get an appointment by their vet ID and order by date and startTime
  public Collection<Appointment> getAppointmentByVetID(int vetID);

  // Get appointments for a specific vet within a week range and order by date and startTime
  public Collection<Appointment> getAppointmentsByVetAndWeek(int vetId, LocalDate startDate, LocalDate endDate);

  // Get an appointment by their pet ID
  public Collection<Appointment> getAppointmentByPetID(int petID);

  // Create a new appointment
  public Appointment createAppointment(Appointment appointment);

  // Get appointments by VetID and Date
  public Collection<Appointment> getAppointmentsByVetIDAndDate(int vetID, LocalDate date);
}