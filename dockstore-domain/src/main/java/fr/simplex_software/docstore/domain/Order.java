package fr.simplex_software.docstore.domain;

import io.quarkus.mongodb.panache.common.*;
import org.bson.codecs.pojo.annotations.*;

import java.math.*;
import java.util.*;

@MongoEntity(collection="Orders")
public class Order
{
  @BsonId
  private BigInteger id;
  private BigInteger customerId;
  private Address shippingAddress;
  private Address billingAddress;
  private Set<OrderItem> orderItemSet = new HashSet<>();

  public Order(BigInteger customerId, Address shippingAddress, Address billingAddress)
  {
    this.customerId = customerId;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
  }

  public BigInteger getId()
  {
    return id;
  }

  public void setId(BigInteger id)
  {
    this.id = id;
  }

  public BigInteger getCustomerId()
  {
    return customerId;
  }

  public void setCustomerId(BigInteger customerId)
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

  public void addOrderItem (OrderItem orderItem)
  {
    orderItemSet.add(orderItem);
  }

  public void removeOrderItem (OrderItem orderItem)
  {
    orderItemSet.remove(orderItem);
  }

  public Set<OrderItem> getOrderItemSet()
  {
    return orderItemSet;
  }

  public void setOrderItemSet(Set<OrderItem> orderItemSet)
  {
    this.orderItemSet = orderItemSet;
  }
}
