package au.edu.rmit.sept.webapp.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

  // Get appointment types by vet id and appointmentTypeId
  @Override
  public Optional<VetAppointmentTypeOffered> getVetAppointmentTypeOfferedByVetIDAndAppointmentTypeID(int vetId, int appointmentTypeId) {
    return vetAppointmentTypeOfferedRepository.findByVetIDAndAppointmentTypeID(vetId, appointmentTypeId);
  }
}