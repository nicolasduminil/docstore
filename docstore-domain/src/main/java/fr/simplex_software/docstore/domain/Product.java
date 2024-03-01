package fr.simplex_software.docstore.domain;

import java.math.*;
import java.util.*;

public class Product
{
  private String id;
  private String name, description;
  private BigDecimal price;
  private Map<String, String> attributes = new HashMap<>();

  public Product() {}

  public Product(String name, String description, BigDecimal price)
  {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Product (String id, Product product)
  {
    this (product.name, product.description, product.price);
    this.id = id;
    this.attributes = product.attributes;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public BigDecimal getPrice()
  {
    return price;
  }

  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }

  public String getAttribute (String attributeName)
  {
    return attributes.get(attributeName);
  }

  public void putAttribute (String attributeName, String attributeValue)
  {
    attributes.put (attributeName, attributeValue);
  }

  public Map<String, String> getAttributes()
  {
    return attributes;
  }

  public void setAttributes(Map<String, String> attributes)
  {
    this.attributes = attributes;
  }
}
