package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Address;

public interface AddressService {
  // Get address by their ID
  public Address getAddressByUserID(int userID);

  // Create a new user
  public Address createAddress(Address address);
}