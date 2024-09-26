package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.ClinicAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ClinicAddressRepository extends JpaRepository<ClinicAddress, Integer> {
    Optional<ClinicAddress> findById(int clinicAddressID);
}
