package fr.simplex_software.docstore.domain;

import io.quarkus.mongodb.panache.common.*;
import jakarta.mail.internet.*;
import org.bson.codecs.pojo.annotations.*;

import java.math.*;
import java.util.*;

@MongoEntity(database = "mdb", collection="Customers")
public class Customer
{
  @BsonId
  private Long id;
  private String firstName, lastName;
  private InternetAddress email;
  private Set<Address> addresses;

  public Customer() {}

  public Customer(String firstName, String lastName)
  {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Customer(String firstName, String lastName, InternetAddress email)
  {
    this (firstName, lastName);
    this.email = email;
  }

  public Customer (Long id, Customer customer)
  {
    this (customer.firstName, customer.getLastName(), customer.email);
    this.addresses = customer.addresses;
    this.id = id;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public InternetAddress getEmail()
  {
    return email;
  }

  public void setEmail(InternetAddress email)
  {
    this.email = email;
  }

  public void addAddress(Address address)
  {
    addresses.add(address);
  }

  public void removeAddress (Address address)
  {
    addresses.remove(address);
  }

  public Set<Address> getAddresses()
  {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses)
  {
    this.addresses = addresses;
  }
}
