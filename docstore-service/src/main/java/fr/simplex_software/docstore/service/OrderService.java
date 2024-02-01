package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.math.*;
import java.util.*;

public interface OrderService
{
  List<Order> findAllOrders();
  Optional<Order> findOrderById (BigInteger id);
  List<Order> findOrdersByAddress (Address address);
  List<Order> findOrdersByOrderItem (OrderItem orderItem);
  List<Order> findOrdersByCustomerId (BigInteger id);
  List<Order> findOrdersByCustomerFirstNameAndLastName (String firstName, String lastName);
  List<Order> findOrdersByCustomerEmail (InternetAddress email);
  List<Order> findOrdersByCustomerAddress (Address address);
  void createOrder (Order order);
  void updateOrder (BigInteger id, Order order);
  void removeOrder (Order order);
}
