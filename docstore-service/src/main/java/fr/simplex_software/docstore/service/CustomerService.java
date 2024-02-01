package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.math.*;
import java.util.*;

public interface CustomerService
{
  List<Customer> findAllCustomers();
  Optional<Customer> findCustomerById (BigInteger id);
  List<Customer> findCustomersByFirstNameAndLastName (String firstName, String lastName);
  Optional<Customer> findCustomerByEmail (InternetAddress emaiAddress);
  List<Customer> findCustomersByAddress (Address address);
  void createCustomer (Customer customer);
  void updateCustomer (BigInteger id, Customer customer);
  void removeCustomer (Customer customer);
}
