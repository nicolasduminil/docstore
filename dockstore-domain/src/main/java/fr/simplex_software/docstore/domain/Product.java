package fr.simplex_software.docstore.domain;

import io.quarkus.mongodb.panache.common.*;
import org.bson.codecs.pojo.annotations.*;

import java.math.*;
import java.util.*;

@MongoEntity(collection="Products")
public class Product
{
  @BsonId
  private BigInteger id;
  private String name, description;
  private BigDecimal price;
  private Map<String, String> attributes = new HashMap<>();

  public Product(String name, String description, BigDecimal price)
  {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public BigInteger getId()
  {
    return id;
  }

  public void setId(BigInteger id)
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
