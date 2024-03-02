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
  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Order order) throws IOException
  {
    IndexRequest<Order> request =
      IndexRequest.of(builder -> builder.index("orders").id(order.getId()).document(order));
    return client.index(request).index();
  }

  @Override
  public Order getOrder(String id) throws IOException
  {
    GetResponse<Order> getResponse =
      client.get(GetRequest.of(builder -> builder.index("orders").id(id)), Order.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<Order> searchOrderByAddress(Address address)
  {
    return null;
  }

  @Override
  public List<Order> searchOrderByCustomerId(String customerId)
  {
    return null;
  }

  @Override
  public List<Order> searchOrder(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index("orders")
        .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), Order.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }
}
