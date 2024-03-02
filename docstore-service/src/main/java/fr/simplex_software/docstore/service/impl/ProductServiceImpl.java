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
public class ProductServiceImpl implements ProductService
{
  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Product product) throws IOException
  {
    IndexRequest<Product> request =
      IndexRequest.of(builder -> builder.index("products").id(product.getId()).document(product));
    return client.index(request).index();
  }

  @Override
  public Product getProduct(String id) throws IOException
  {
    GetResponse<Product> getResponse =
      client.get(GetRequest.of(builder -> builder.index("products").id(id)), Product.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<Product> searchProductByAddress(Address address)
  {
    return null;
  }

  @Override
  public List<Product> searchProductByName(String name)
  {
    return null;
  }

  @Override
  public List<Product> searchProduct(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index("products")
      .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), Product.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }
}
