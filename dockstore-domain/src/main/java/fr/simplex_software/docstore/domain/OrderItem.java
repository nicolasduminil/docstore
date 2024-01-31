package fr.simplex_software.docstore.domain;

import java.math.*;

public class OrderItem
{
  private BigInteger productId;
  private BigDecimal price;
  private int amount;

  public OrderItem(BigInteger productId, BigDecimal price, int amount)
  {
    this.productId = productId;
    this.price = price;
    this.amount = amount;
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
