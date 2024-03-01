package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.util.*;

public interface OrderService
{
  void doIndex (Order order);
  Order getOrder (String id);
  List<Order> searchOrderByAddress (Address address);
  List<Order> searchOrderByCustomerId (String customerId);
  List<Order> searchOrder (String term, String match);
}
