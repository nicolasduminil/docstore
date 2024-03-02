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
  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(OrderItem orderItem) throws IOException
  {
    IndexRequest<OrderItem> request =
      IndexRequest.of(builder -> builder.index("order-items").id(orderItem.getId()).document(orderItem));
    return client.index(request).index();
  }

  @Override
  public OrderItem getOrderItem(String id) throws IOException
  {
    GetRequest getRequest = GetRequest.of(builder -> builder.index("order-items").id(id));
    GetResponse<OrderItem> getResponse = client.get(getRequest, OrderItem.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<OrderItem> searchOrderItemByProductId(String productId)
  {
    return null;
  }

  @Override
  public List<OrderItem> searchOrderItem(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index("order-items")
        .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), OrderItem.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }
}
