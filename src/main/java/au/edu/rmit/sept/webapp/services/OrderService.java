package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Order;

public interface OrderService {
  // Create a new order
  public Order createOrder(Order order);
}