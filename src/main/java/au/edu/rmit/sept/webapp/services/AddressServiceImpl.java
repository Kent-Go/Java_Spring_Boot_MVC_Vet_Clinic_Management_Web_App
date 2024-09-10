package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

  private AddressRepository addressRepository;

  @Autowired
  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public Address getAddressByUserID(int userID) {
    return addressRepository.findByUserID(userID).orElse(null);
  }

  @Override
  public Address createAddress(Address address) {
    return addressRepository.save(address);
  }
}