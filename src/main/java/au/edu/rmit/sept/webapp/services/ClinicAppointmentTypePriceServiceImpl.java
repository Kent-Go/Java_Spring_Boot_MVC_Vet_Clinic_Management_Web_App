package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.ClinicAppointmentTypePrice;
import au.edu.rmit.sept.webapp.repositories.ClinicAppointmentTypePriceRepository;

@Service
public class ClinicAppointmentTypePriceServiceImpl implements ClinicAppointmentTypePriceService {

  private ClinicAppointmentTypePriceRepository clinicAppointmentTypePriceRepository;

  @Autowired
  public ClinicAppointmentTypePriceServiceImpl(ClinicAppointmentTypePriceRepository clinicAppointmentTypePriceRepository) {
    this.clinicAppointmentTypePriceRepository = clinicAppointmentTypePriceRepository;
  }

  @Override
  public ClinicAppointmentTypePrice getClinicAppointmentTypePriceByClinicIDAndAppointmentTypeID(int clinicID, int appointmentTypeID) {
    return clinicAppointmentTypePriceRepository.findByClinicIDAndAppointmentTypeID(clinicID, appointmentTypeID);
  }

}