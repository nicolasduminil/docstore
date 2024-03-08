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
  private static final String INDEX = "products";

  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Product product) throws IOException
  {
    return client.index(IndexRequest.of(ir -> ir.index(INDEX).document(product))).id();
  }

  @Override
  public Product getProduct(String id) throws IOException
  {
     GetResponse<Product> getResponse =
      client.get(GetRequest.of(gr -> gr.index(INDEX).id(id)), Product.class);
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
    return client.search(SearchRequest.of(sr -> sr.index(INDEX)
      .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), Product.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }

  @Override
  public void modifyProduct(Product product) throws IOException
  {
    client.update(ur -> ur.index(INDEX).id(product.getId()).doc(product), Product.class);
  }

  @Override
  public void removeProductById(String id) throws IOException
  {
    client.delete(dr -> dr.index(INDEX).id(id));
  }

  @Override
  public void removeProduct(String field, String value) throws IOException
  {
    client.deleteByQuery(DeleteByQueryRequest.of(dbqr -> dbqr.query(TermQuery.of(tq -> tq.field(field).value(value))._toQuery()).index(INDEX)));
  }

  @Override
  public void removeAllProduct() throws IOException
  {
    client.delete(dr -> dr.index(INDEX));
  }
}
