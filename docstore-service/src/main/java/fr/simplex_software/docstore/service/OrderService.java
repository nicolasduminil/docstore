package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.io.*;
import java.util.*;

public interface OrderService
{
  String doIndex (Order order) throws IOException;
  Order getOrder (String id) throws IOException;
  List<Order> searchOrderByAddress (Address address) throws IOException;
  List<Order> searchOrderByCustomerId (String customerId) throws IOException;
  List<Order> searchOrder (String term, String match) throws IOException;
  void modifyOrder (Order order) throws IOException;
  void removeOrderById (String id) throws IOException;
  void removeOrder (String term, String match) throws IOException;
  void removeAllOrders() throws IOException;
}
