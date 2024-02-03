package fr.simplex_software.docstore.domain;

import io.quarkus.mongodb.panache.common.*;
import org.bson.codecs.pojo.annotations.*;

import java.math.*;

@MongoEntity(database = "mdb", collection="OrderItems")
public class OrderItem
{
  @BsonId
  private BigInteger id;
  private BigInteger productId;
  private BigDecimal price;
  private int amount;

  public OrderItem() {}

  public OrderItem(BigInteger productId, BigDecimal price, int amount)
  {
    this.productId = productId;
    this.price = price;
    this.amount = amount;
  }

  public OrderItem(BigInteger id, OrderItem orderItem)
  {
    this (orderItem.productId, orderItem.price, orderItem.amount);
    this.id = orderItem.id;
  }

  public BigInteger getId()
  {
    return id;
  }

  public void setId(BigInteger id)
  {
    this.id = id;
  }

  public BigInteger getProductId()
  {
    return productId;
  }

  public void setProductId(BigInteger productId)
  {
    this.productId = productId;
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
