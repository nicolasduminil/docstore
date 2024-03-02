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
  @Inject
  ElasticsearchClient client;

  @Override
  public String doIndex(Customer customer) throws IOException
  {
    IndexRequest<Customer> request =
      IndexRequest.of(builder -> builder.index("customers").id(customer.getId()).document(customer));
    return client.index(request).index();
  }

  @Override
  public Customer getCustomer(String id) throws IOException
  {
    GetRequest getRequest = GetRequest.of(builder -> builder.index("customers").id(id));
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
  public List<Customer> searchCustomer(String term, String match) throws IOException
  {
    return client.search(SearchRequest.of(builder -> builder.index("customers")
      .query(QueryBuilders.match().field(term).query(FieldValue.of(match)).build()._toQuery())), Customer.class).hits()
      .hits().stream().map(Hit::source).collect(Collectors.toList());
  }
}
