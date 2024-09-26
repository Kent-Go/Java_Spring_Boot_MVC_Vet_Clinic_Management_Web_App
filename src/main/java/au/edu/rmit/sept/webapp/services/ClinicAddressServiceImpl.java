package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.ClinicAddress;
import au.edu.rmit.sept.webapp.repositories.ClinicAddressRepository;

@Service
public class ClinicAddressServiceImpl implements ClinicAddressService {

  private ClinicAddressRepository clinicAddressRepository;

  @Autowired
  public ClinicAddressServiceImpl(ClinicAddressRepository clinicAddressRepository) {
    this.clinicAddressRepository = clinicAddressRepository;
  }

  @Override
  public ClinicAddress getClinicAddressByClinicAddressID(int clinicAddressID) {
    return clinicAddressRepository.findById(clinicAddressID).orElse(null);
  }
}