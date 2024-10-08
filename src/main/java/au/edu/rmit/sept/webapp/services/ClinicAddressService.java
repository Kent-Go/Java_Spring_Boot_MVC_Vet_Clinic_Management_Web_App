package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.ClinicAddress;

public interface ClinicAddressService {
  // Get clinic address by clinic id
  public ClinicAddress getClinicAddressByClinicAddressID(int clinicAddressID);
}