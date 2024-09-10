package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
