package fr.simplex_software.docstore.service.impl;

import com.mongodb.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import fr.simplex_software.docstore.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.mail.internet.*;
import jakarta.ws.rs.*;

import java.util.*;
import java.util.stream.*;

@ApplicationScoped
public class OrderServiceImpl implements OrderService
{
  public static final String FMT = "### OrderServiceImpl.findOrdersByCustomerId(): Can't find customer having id %d";
  public static final String FMT2 = "### OrderServiceImpl.findOrdersByCustomerEmail(): Can't find customer having email %S";

  @Inject
  OrderRepository orderRepository;
  @Inject
  CustomerService customerService;

  @Override
  public List<Order> findAllOrders()
  {
    return orderRepository.listAll();
  }

  @Override
  public Optional<Order> findOrderById(Long id)
  {
    return orderRepository.findByIdOptional(id);
  }

  @Override
  public List<Order> findOrdersByAddress(Address address)
  {
    return orderRepository.find("shippingAddress = ?1 or billingAddress = ?1", address).list();
  }

  @Override
  public List<Order> findOrdersByOrderItem(OrderItem orderItem)
  {
    return orderRepository.find("?1 in orderItemSet", orderItem).list();
  }

  @Override
  public List<Order> findOrdersByCustomer(DBRef customer)
  {
    Long id = (Long)customer.getId();
    return orderRepository.find("customerId = ?1", customerService.findCustomerById(id)
      .orElseThrow(() -> new NotFoundException(String.format(FMT, id)))).list();
  }

  @Override
  public List<Order> findOrdersByCustomerFirstNameAndLastName(String firstName, String lastName)
  {
    return orderRepository.find ("customerId in ?1",
      customerService.findCustomersByFirstNameAndLastName(firstName, lastName).stream().map(Customer::getId)
        .collect(Collectors.toList())).list();
  }

  @Override
  public List<Order> findOrdersByCustomerEmail(InternetAddress email)
  {
    return orderRepository.find("customerId = ?1", customerService.findCustomerByEmail(email)
      .orElseThrow(() -> new NotFoundException(String.format(FMT2, email))).getId()).list();
  }

  @Override
  public List<Order> findOrdersByCustomerAddress(Address address)
  {
    return orderRepository.find("customerId in  ?1",
      customerService.findCustomersByAddress(address).stream().map(Customer::getId).collect(Collectors.toList())).list();
  }

  @Override
  public void createOrder(Order order)
  {
    orderRepository.persist(order);
  }

  @Override
  public void updateOrder(Long id, Order order)
  {
    orderRepository.persistOrUpdate(new Order (id, order));
  }

  @Override
  public void removeOrder(Order order)
  {
    orderRepository.delete(order);
  }
}
