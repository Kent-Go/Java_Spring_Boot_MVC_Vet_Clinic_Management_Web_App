package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

  private AppointmentRepository appointmentRepository;

  @Autowired
  public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public Collection<Appointment> getAllAppointments() {
    return appointmentRepository.findAll();
  }

  @Override
  public Appointment getAppointmentByVetID(int vetID) {
    return appointmentRepository.findById(vetID).orElseThrow(() -> new RuntimeException("Appointment not found"));
  }

  @Override
  public Appointment getAppointmentByPetID(int petID) {
    return appointmentRepository.findById(petID).orElseThrow(() -> new RuntimeException("Appointment not found"));
  }

  @Override
  public Appointment createAppointment(Appointment appointment) {
    return appointmentRepository.save(appointment);
  }
}