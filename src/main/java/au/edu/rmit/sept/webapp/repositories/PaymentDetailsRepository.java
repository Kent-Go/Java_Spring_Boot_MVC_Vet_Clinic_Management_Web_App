package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
}
