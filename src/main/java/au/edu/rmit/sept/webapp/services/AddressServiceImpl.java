package au.edu.rmit.sept.webapp.services;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
  public Collection<Address> getAllAddresses() {
    return addressRepository.findAll();
  }

  @Override
  public Address getAddressByUserID(int userID) {
    return addressRepository.findById(userID).orElseThrow(() -> new RuntimeException("Address not found"));
  }

  @Override
  public Address createAddress(Address address) {
    return addressRepository.save(address);
  }
}