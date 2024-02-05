package fr.simplex_software.docstore.service;

import com.mongodb.*;
import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.math.*;
import java.util.*;

public interface OrderService
{
  List<Order> findAllOrders();
  Optional<Order> findOrderById (Long id);
  List<Order> findOrdersByAddress (Address address);
  List<Order> findOrdersByOrderItem (OrderItem orderItem);
  List<Order> findOrdersByCustomer (DBRef customer);
  List<Order> findOrdersByCustomerFirstNameAndLastName (String firstName, String lastName);
  List<Order> findOrdersByCustomerEmail (InternetAddress email);
  List<Order> findOrdersByCustomerAddress (Address address);
  void createOrder (Order order);
  void updateOrder (Long id, Order order);
  void removeOrder (Order order);
}
