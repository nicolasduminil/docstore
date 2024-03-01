package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;

import java.util.*;

public interface OrderRepository
{
  void index (Order order);
  Order get (String id);
  List<Order> searchByAddress (Address address);
  List<Order> searchByCustomerId (String customerId);
  List<Order> search (String term, String match);
}
