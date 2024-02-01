package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.math.*;
import java.util.*;

public interface OrderItemService
{
  List<OrderItem> findAllOrderItems();
  Optional<OrderItem> findOrderItemById (BigInteger id);
  List<OrderItem> findOrdersItemByProductId (BigInteger id);
  List<OrderItem> findOrderItemsByProductName (String name);
  List<OrderItem> findOrderItemsByProductNameAndPrice (String name, BigDecimal price);
  List<OrderItem> findOrderItemsByProductAttribute (String key, String value);
  List<OrderItem> findOrderItemsByProductAttributes (HashMap<String, String> attributes);
  void createOrderItem (OrderItem orderItem);
  void updateOrderItem (BigInteger id, OrderItem orderItem);
  void removeProduct (OrderItem orderItem);
}
