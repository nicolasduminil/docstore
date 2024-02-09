package fr.simplex_software.docstore.domain;

import com.fasterxml.jackson.databind.annotation.*;
import com.mongodb.*;
import fr.simplex_software.docstore.domain.serializers.*;
import io.quarkus.mongodb.panache.common.*;
import org.bson.codecs.pojo.annotations.*;

import java.math.*;

@MongoEntity(database = "mdb", collection="OrderItems")
public class OrderItem
{
  @BsonId
  private Long id;
  private DBRef product;
  private BigDecimal price;
  private int amount;

  public OrderItem() {}

  public OrderItem(DBRef product, BigDecimal price, int amount)
  {
    this.product = product;
    this.price = price;
    this.amount = amount;
  }

  public OrderItem(Long id, OrderItem orderItem)
  {
    this (orderItem.product, orderItem.price, orderItem.amount);
    this.id = orderItem.id;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public DBRef getProduct()
  {
    return product;
  }

  public void setProduct(DBRef product)
  {
    this.product = product;
  }

  public BigDecimal getPrice()
  {
    return price;
  }

  public void setPrice(BigDecimal price)
  {
    this.price = price;
  }

  public int getAmount()
  {
    return amount;
  }

  public void setAmount(int amount)
  {
    this.amount = amount;
  }
}
