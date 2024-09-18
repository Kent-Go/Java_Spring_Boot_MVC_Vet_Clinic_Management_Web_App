package au.edu.rmit.sept.webapp.services;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.VetAppointmentTypeOffered;
import au.edu.rmit.sept.webapp.repositories.VetAppointmentTypeOfferedRepository;

@Service
public class VetAppointmentTypeOfferedServiceImpl implements VetAppointmentTypeOfferedService {

  private VetAppointmentTypeOfferedRepository vetAppointmentTypeOfferedRepository;

  @Autowired
  public VetAppointmentTypeOfferedServiceImpl(VetAppointmentTypeOfferedRepository vetAppointmentTypeOfferedRepository) {
    this.vetAppointmentTypeOfferedRepository = vetAppointmentTypeOfferedRepository;
  }

  // Get appointments by their appointmentTypeId
  @Override
  public Collection<VetAppointmentTypeOffered> getVetAppointmentTypeOfferedByAppointmentTypeID(int appointmentTypeId) {
    return vetAppointmentTypeOfferedRepository.findByAppointmentTypeID(appointmentTypeId);
  }
}