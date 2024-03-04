package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.io.*;
import java.util.*;

public interface OrderItemService
{
  String doIndex (OrderItem orderItem) throws IOException;
  OrderItem getOrderItem (String id) throws IOException;
  List<OrderItem> searchOrderItemByProductId (String productId) throws IOException;
  List<OrderItem> searchOrderItem (String field, String value) throws IOException;
  void modifyOrderItem (OrderItem orderItem) throws IOException;
  void removeOrderItemById (String id) throws IOException;
  void removeOrderItem (String term, String match) throws IOException;
  void removeAllOrderItems() throws IOException;
}
