package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;

import au.edu.rmit.sept.webapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
