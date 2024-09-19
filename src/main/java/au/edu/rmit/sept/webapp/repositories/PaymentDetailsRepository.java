package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
}
