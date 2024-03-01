package fr.simplex_software.docstore.repository.impl;

import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import jakarta.mail.internet.*;

import java.util.*;

public class CustomerRepositoryImpl implements CustomerRepository
{
  @Override
  public void index(Customer customer)
  {

  }

  @Override
  public Customer get(String id)
  {
    return null;
  }

  @Override
  public List<Customer> searchByAddress(Address address)
  {
    return null;
  }

  @Override
  public List<Customer> searchByEmailAddress(InternetAddress emailAddress)
  {
    return null;
  }

  @Override
  public List<Customer> search(String term, String match)
  {
    return null;
  }
}
