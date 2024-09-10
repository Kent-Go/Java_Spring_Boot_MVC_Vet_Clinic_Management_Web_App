package au.edu.rmit.sept.webapp.restcontrollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Address;
import au.edu.rmit.sept.webapp.repositories.AddressRepository;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressRestController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }
}
