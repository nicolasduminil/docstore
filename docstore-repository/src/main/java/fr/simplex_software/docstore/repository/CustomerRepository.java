package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.util.*;

public interface CustomerRepository
{
  void index (Customer customer);
  Customer get (String id);
  List<Customer> searchByAddress (Address address);
  List<Customer> searchByEmailAddress (InternetAddress emailAddress);
  List<Customer> search (String term, String match);
}
