package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.util.*;

public interface OrderItemService
{
  void doIndex (OrderItem orderItem);
  OrderItem getOrderItem (String id);
  List<OrderItem> searchOrderItemByProductId (String productId);
  List<OrderItem> searchOrderItem (String term, String match);
}
