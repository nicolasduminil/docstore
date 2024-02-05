package fr.simplex_software.docstore.service.impl;

import com.mongodb.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import fr.simplex_software.docstore.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;

import java.math.*;
import java.util.*;
import java.util.stream.*;

@ApplicationScoped
public class OrderItemServiceImpl implements OrderItemService
{
  public static final String FMT = "### OrderItemServiceImpl.findOrderItemsByProductId(): Can't find product having id %d";

  @Inject
  OrderItemRepository orderItemRepository;
  @Inject
  ProductService productService;

  @Override
  public List<OrderItem> findAllOrderItems()
  {
    return orderItemRepository.listAll();
  }

  @Override
  public Optional<OrderItem> findOrderItemById(Long id)
  {
    return orderItemRepository.findByIdOptional(id);
  }

  @Override
  public List<OrderItem> findOrdersItemByProduct(DBRef product)
  {
    Long id = (Long)product.getId();
    return orderItemRepository.find("productId = ?1", productService.findProductById(id)
      .orElseThrow(() -> new NotFoundException(String.format(FMT, id)))).list();
  }

  @Override
  public List<OrderItem> findOrderItemsByProductName(String name)
  {
    return orderItemRepository.find ("orderId in ?1",
      productService.findProductsByName(name).stream().map(Product::getId)
        .collect(Collectors.toList())).list();
  }

  @Override
  public List<OrderItem> findOrderItemsByProductNameAndPrice(String name, BigDecimal price)
  {
    return orderItemRepository.find ("productId in ?1",
      productService.findProductsByNameAndPrice(name, price).stream().map(Product::getId)
        .collect(Collectors.toList())).list();
  }

  @Override
  public List<OrderItem> findOrderItemsByProductAttribute(String key, String value)
  {
    return orderItemRepository.find ("productId in ?1", productService.findProductsByAttribute(key, value)
      .stream().map(Product::getId).collect(Collectors.toList())).list();
  }

  @Override
  public List<OrderItem> findOrderItemsByProductAttributes(HashMap<String, String> attributes)
  {
    return orderItemRepository.find ("productId in ?1", productService.findProductsByAttributes(attributes)
      .stream().map(Product::getId).collect(Collectors.toList())).list();
  }

  @Override
  public void createOrderItem(OrderItem orderItem)
  {
    orderItemRepository.persist(orderItem);
  }

  @Override
  public void updateOrderItem(Long id, OrderItem orderItem)
  {
    orderItemRepository.persistOrUpdate(new OrderItem (id, orderItem));
  }

  @Override
  public void removeProduct(OrderItem orderItem)
  {
    orderItemRepository.delete(orderItem);
  }
}
