package fr.simplex_software.docstore.domain;

import java.math.*;

public class OrderItem
{
  private String id;
  private String productId;
  private BigDecimal price;
  private int amount;

  public OrderItem() {}

  public OrderItem(String productId, BigDecimal price, int amount)
  {
    this.productId = productId;
    this.price = price;
    this.amount = amount;
  }

  public OrderItem(String id, OrderItem orderItem)
  {
    this (orderItem.productId, orderItem.price, orderItem.amount);
    this.id = orderItem.id;
  }

  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getProductId()
  {
    return productId;
  }

  public void setProductId(String productId)
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
