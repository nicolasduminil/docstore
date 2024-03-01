package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;
import jakarta.mail.internet.*;

import java.util.*;

public interface OrderItemRepository
{
  void index (OrderItem orderItem);
  OrderItem get (String id);
  List<OrderItem> searchByProductId (String productId);
  List<OrderItem> search (String term, String match);
}
