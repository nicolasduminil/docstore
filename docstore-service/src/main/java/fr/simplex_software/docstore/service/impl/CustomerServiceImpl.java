package fr.simplex_software.docstore.service.impl;

import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import fr.simplex_software.docstore.service.*;
import jakarta.inject.*;
import jakarta.mail.internet.*;

import java.math.*;
import java.util.*;

public class CustomerServiceImpl implements CustomerService
{
  @Inject
  CustomerRepository customerRepository;

  @Override
  public Optional<Customer> findCustomerById(BigInteger id)
  {
    return customerRepository.findByIdOptional(id);
  }

  @Override
  public List<Customer> findCustomersByFirstNameAndLastName(String firstName, String lastName)
  {
    return customerRepository.find("firstName = ?1 and lastName = ?2", firstName, lastName).list();
  }

  @Override
  public Optional<Customer> findCustomerByEmail(InternetAddress emaiAddress)
  {
    return Optional.of(customerRepository.find("email = ?1", emaiAddress).firstResult());
  }

  @Override
  public List<Customer> findCustomersByAddress(Address address)
  {
    return customerRepository.find("?1 in addresses", address).list();
  }

  @Override
  public void createCustomer(Customer customer)
  {
    customerRepository.persist(customer);
  }

  @Override
  public void updateCustomer(BigInteger id, Customer customer)
  {
    customerRepository.persistOrUpdate(new Customer (id, customer));
  }

  @Override
  public void removeCustomer(Customer customer)
  {
    customerRepository.delete(customer);
  }
}
