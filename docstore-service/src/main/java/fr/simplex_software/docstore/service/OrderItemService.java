package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.io.*;
import java.util.*;

public interface OrderItemService
{
  String doIndex (OrderItem orderItem) throws IOException;
  OrderItem getOrderItem (String id) throws IOException;
  List<OrderItem> searchOrderItemByProductId (String productId);
  List<OrderItem> searchOrderItem (String term, String match) throws IOException;
}
