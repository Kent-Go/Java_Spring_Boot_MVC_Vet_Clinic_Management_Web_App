package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByUserID(int userID);
}
