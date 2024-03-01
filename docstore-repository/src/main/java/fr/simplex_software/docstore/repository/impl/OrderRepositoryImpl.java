package fr.simplex_software.docstore.repository.impl;

import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;

import java.util.*;

public class OrderRepositoryImpl implements OrderRepository
{
  @Override
  public void index(Order order)
  {

  }

  @Override
  public Order get(String id)
  {
    return null;
  }

  @Override
  public List<Order> searchByAddress(Address address)
  {
    return null;
  }

  @Override
  public List<Order> searchByCustomerId(String customerId)
  {
    return null;
  }

  @Override
  public List<Order> search(String term, String match)
  {
    return null;
  }
}
