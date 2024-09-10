package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
