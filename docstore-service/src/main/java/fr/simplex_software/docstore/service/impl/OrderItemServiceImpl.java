package fr.simplex_software.docstore.service.impl;

import co.elastic.clients.elasticsearch.*;
import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@ApplicationScoped
public class OrderItemServiceImpl implements OrderItemService
{
  private static final String INDEX = "order_items";
  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(OrderItem orderItem) throws IOException
  {
    return client.index(IndexRequest.of(builder -> builder.index(INDEX).document(orderItem))).id();
  }

  @Override
  public OrderItem getOrderItem(String id) throws IOException
  {
    GetRequest getRequest = GetRequest.of(builder -> builder.index(INDEX).id(id));
    GetResponse<OrderItem> getResponse = client.get(getRequest, OrderItem.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<OrderItem> searchOrderItemByProductId(String productId) throws IOException
  {
    return searchOrderItem("productId", productId);
  }

  @Override
  public List<OrderItem> searchOrderItem(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index(INDEX)
        .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), OrderItem.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }

  @Override
  public void modifyOrderItem(OrderItem orderItem) throws IOException
  {
    client.update(ur -> ur.index(INDEX).id(orderItem.getId()).doc(orderItem), OrderItem.class);
  }

  @Override
  public void removeOrderItemById(String id) throws IOException
  {
    client.delete(dr -> dr.index(INDEX).id(id));
  }

  @Override
  public void removeOrderItem(String field, String value) throws IOException
  {
    client.deleteByQuery(DeleteByQueryRequest.of(dbqr -> dbqr.query(TermQuery.of(tq -> tq.field(field).value(value))._toQuery()).index(INDEX)));
  }

  @Override
  public void removeAllOrderItems() throws IOException
  {
    client.delete(dr -> dr.index(INDEX));
  }
}
