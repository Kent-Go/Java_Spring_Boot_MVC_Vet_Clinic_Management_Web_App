package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import java.util.Date;

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

  @Override
  public Collection<Appointment> getAllAppointments() {
    return appointmentRepository.findAll();
  }

  @Override
  public Collection<Appointment> getAppointmentByVetID(int vetID) {
    return appointmentRepository.findByVetID(vetID);
  }

  @Override
  public Collection<Appointment> getAppointmentByPetID(int petID) {
    return appointmentRepository.findByPetID(petID);
  }

  @Override
  public Appointment createAppointment(Appointment appointment) {
    return appointmentRepository.save(appointment);
  }

  @Override
  public Collection<Appointment> getAppointmentsByVetIDAndDate(int vetID, Date date) {
    return appointmentRepository.findByVetIDAndDate(vetID, date);
  }
}