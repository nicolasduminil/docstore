package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.io.*;
import java.util.*;

public interface OrderService
{
  String doIndex (Order order) throws IOException;
  Order getOrder (String id) throws IOException;
  List<Order> searchOrderByAddress (Address address);
  List<Order> searchOrderByCustomerId (String customerId);
  List<Order> searchOrder (String term, String match) throws IOException;
}
