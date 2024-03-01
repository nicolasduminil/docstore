package fr.simplex_software.docstore.domain;

import jakarta.mail.internet.*;

import java.util.*;

public class Customer
{
  private String id;
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
    this.addresses = new HashSet<>();
  }

  public Customer (String id, Customer customer)
  {
    this (customer.firstName, customer.getLastName(), customer.email);
    this.addresses = customer.addresses;
    this.id = id;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
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
