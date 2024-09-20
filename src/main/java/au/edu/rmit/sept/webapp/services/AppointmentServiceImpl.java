package au.edu.rmit.sept.webapp.services;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private AppointmentRepository appointmentRepository;

  @Autowired
  public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  // Get all the appointments
  @Override
  public Collection<Appointment> getAllAppointments() {
    return appointmentRepository.findAll();
  }

  // Get appointments for a specific vet and order by date and startTime
  @Override
  public Collection<Appointment> getAppointmentByVetID(int vetID) {
    return appointmentRepository.findByVetIDOrderByDateAscStartTimeAsc(vetID);
  }

  // Get appointments for a specific vet within a week range and order by date and
  // startTime
  @Override
  public Collection<Appointment> getAppointmentsByVetAndWeek(int vetID, LocalDate startDate, LocalDate endDate) {
    return appointmentRepository.findByVetIDAndDateBetweenOrderByDateAscStartTimeAsc(vetID, startDate, endDate);
  }

  // Get an appointment by their petID
  @Override
  public Collection<Appointment> getAppointmentByPetID(int petID) {
    return appointmentRepository.findByPetID(petID);
  }

  // create a new appointment
  @Override
  public Appointment createAppointment(Appointment appointment) {
    return appointmentRepository.save(appointment);
  }

  // Get appointments by their vetID and Date and order by startTime
  @Override
  public Collection<Appointment> getAppointmentsByVetIDAndDate(int vetID, LocalDate date) {
    return appointmentRepository.findByVetIDAndDateOrderByStartTimeAsc(vetID, date);
  }

  // Get appointments by their petID after a certain date
  @Override
  public Collection<Appointment> getAppointmentsByPetIDAndDateAfter(int petID, LocalDate date) {
    return appointmentRepository.findByPetIDAndDateAfterOrderByDateAscStartTimeAsc(petID, date);
  }
}