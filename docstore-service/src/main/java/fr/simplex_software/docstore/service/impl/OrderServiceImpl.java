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
public class OrderServiceImpl implements OrderService
{
  private static final String INDEX = "orders";

  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Order order) throws IOException
  {
    return client.index(IndexRequest.of(ir -> ir.index(INDEX).document(order))).id();
  }

  @Override
  public Order getOrder(String id) throws IOException
  {
    GetResponse<Order> getResponse =
      client.get(GetRequest.of(builder -> builder.index(INDEX).id(id)), Order.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<Order> searchOrderByAddress(Address address) throws IOException
  {
    return searchOrder ("address", address.toString());
  }

  @Override
  public List<Order> searchOrderByCustomerId(String customerId) throws IOException
  {
    return searchOrder ("customerId", customerId);
  }

  @Override
  public List<Order> searchOrder(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index(INDEX)
        .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), Order.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }

  @Override
  public void modifyOrder(Order order) throws IOException
  {
    client.update(ur -> ur.index(INDEX).id(order.getId()).doc(order), Order.class);
  }

  @Override
  public void removeOrderById(String id) throws IOException
  {
    client.delete(dr -> dr.index(INDEX).id(id));
  }

  @Override
  public void removeOrder(String field, String value) throws IOException
  {
    client.deleteByQuery(DeleteByQueryRequest.of(dbqr -> dbqr.query(TermQuery.of(tq -> tq.field(field).value(value))._toQuery()).index(INDEX)));
  }

  @Override
  public void removeAllOrders() throws IOException
  {
    client.delete(dr -> dr.index(INDEX));
  }
}
