package fr.simplex_software.docstore.domain;

import com.mongodb.*;
import io.quarkus.mongodb.panache.common.*;
import org.bson.codecs.pojo.annotations.*;

import java.util.*;

@MongoEntity(database = "mdb", collection="Orders")
public class Order
{
  @BsonId
  private Long id;
  private DBRef customer;
  private Address shippingAddress;
  private Address billingAddress;
  private Set<DBRef> orderItemSet = new HashSet<>();

  public Order() {}

  public Order(DBRef customer, Address shippingAddress, Address billingAddress)
  {
    this.customer = customer;
    this.shippingAddress = shippingAddress;
    this.billingAddress = billingAddress;
  }

  public Order (Long id, Order order)
  {
    this (order.customer, order.shippingAddress, order.billingAddress);
    this.id = id;
    this.orderItemSet = order.orderItemSet;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public DBRef getCustomer()
  {
    return customer;
  }

  public void setCustomer(DBRef customer)
  {
    this.customer = customer;
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

  public void addOrderItem (DBRef orderItem)
  {
    orderItemSet.add(orderItem);
  }

  public void removeOrderItem (DBRef orderItem)
  {
    orderItemSet.remove(orderItem);
  }

  public Set<DBRef> getOrderItemSet()
  {
    return orderItemSet;
  }

  public void setOrderItemSet(Set<DBRef> orderItemSet)
  {
    this.orderItemSet = orderItemSet;
  }
}
