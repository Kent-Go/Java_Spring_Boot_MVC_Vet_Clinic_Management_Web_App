package au.edu.rmit.sept.webapp.services;

import java.time.LocalDate;
import java.util.Collection;

import au.edu.rmit.sept.webapp.models.ClinicAddress;

public interface ClinicAddressService {
  // Get clinic address by clinic id
  public ClinicAddress getClinicAddressByClinicAddressID(int clinicAddressID);
}