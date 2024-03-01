package fr.simplex_software.docstore.domain;

import java.util.*;

public class Order
{
  private String id;
  private String customerId;
  private Address shippingAddress;
  private Address billingAddress;
  private Set<String> orderItemIdSet = new HashSet<>();

  public Order() {}

  public Order(String customerId, Address shippingAddress, Address billingAddress)
  {
    this.customerId = customerId;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
  }

  public Order (String id, Order order)
  {
    this (order.customerId, order.shippingAddress, order.billingAddress);
    this.id = id;
    this.orderItemIdSet = order.orderItemIdSet;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getCustomerId()
  {
    return customerId;
  }

  public void setCustomerId(String customerId)
  {
    this.customerId = customerId;
  }

  public Address getShippingAddress()
  {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress)
  {
    this.shippingAddress = shippingAddress;
  }

  public Address getBillingAddress()
  {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress)
  {
    this.billingAddress = billingAddress;
  }

  public void addOrderItemId (String orderItemId)
  {
    orderItemIdSet.add(orderItemId);
  }

  public void removeOrderItemId (String orderItemId)
  {
    orderItemIdSet.remove(orderItemId);
  }

  public Set<String> getOrderItemIdSet()
  {
    return orderItemIdSet;
  }

  public void setOrderItemIdSet(Set<String> orderItemIdSet)
  {
    this.orderItemIdSet = orderItemIdSet;
  }
}
