package au.edu.rmit.sept.webapp.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Order;
import au.edu.rmit.sept.webapp.repositories.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Autowired
  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  // Create a new order and save it to the database
  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Order getOrderByID(int id) {
    return orderRepository.findById(id).orElse(null);
  }
}