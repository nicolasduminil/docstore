package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.io.*;
import java.util.*;

public interface CustomerService
{
  Customer doIndex (Customer customer) throws IOException;
  Customer getCustomer (String id) throws IOException;
  List<Customer> searchCustomerByAddress (Address address) throws IOException;
  List<Customer> searchCustomerByEmailAddress (InternetAddress emailAddress) throws IOException;
  List<Customer> searchCustomer (String term, String match) throws IOException;
  Customer modifyCustomer (Customer customer) throws IOException;
  void removeCustomerById (String id) throws IOException;
  void removeCustomer (String term, String match);
  void removeAllCustomers();
}
