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
import jakarta.mail.internet.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService
{
  private static final String INDEX = "customers";

  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Customer customer) throws IOException
  {
    return client.index(IndexRequest.of(builder -> builder.index(INDEX).document(customer))).id();
  }

  @Override
  public Customer getCustomer(String id) throws IOException
  {
    GetRequest getRequest = GetRequest.of(gr -> gr.index(INDEX).id(id));
    GetResponse<Customer> getResponse = client.get(getRequest, Customer.class);
    return getResponse.found() ? getResponse.source() : null;
  }

  @Override
  public List<Customer> searchCustomerByAddress(Address address) throws IOException
  {
    return searchCustomer("address", address.toString());
  }

  @Override
  public List<Customer> searchCustomerByEmailAddress(InternetAddress emailAddress) throws IOException
  {
    return searchCustomer("email", emailAddress.getAddress());
  }

  @Override
  public List<Customer> searchCustomer(String field, String value) throws IOException
  {
    return client.search(SearchRequest.of(sr -> sr.index(INDEX)
      .query(QueryBuilders.match().field(field).query(FieldValue.of(value)).build()._toQuery())), Customer.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }

  @Override
  public void modifyCustomer(Customer customer) throws IOException
  {
    client.update(ur -> ur.index(INDEX).id(customer.getId()).doc(customer), Customer.class); //.get().source();
  }

  @Override
  public void removeCustomerById(String id) throws IOException
  {
    client.delete(dr -> dr.index(INDEX).id(id));
  }

  @Override
  public void removeCustomer(String field, String value) throws IOException
  {
    client.deleteByQuery(DeleteByQueryRequest.of(dbqr -> dbqr.query(TermQuery.of(tq -> tq.field(field).value(value))._toQuery()).index(INDEX)));
  }

  @Override
  public void removeAllCustomers() throws IOException
  {
    client.delete(dr -> dr.index(INDEX));
  }
}
