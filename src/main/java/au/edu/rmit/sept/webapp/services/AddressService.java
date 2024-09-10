package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import au.edu.rmit.sept.webapp.models.Address;

public interface AddressService {
  // Get all the address
  public Collection<Address> getAllAddresses();

  // Get a address by their ID
  public Address getAddressByUserID(int userID);

  // Create a new address
  public Address createAddress(Address address);
  
}