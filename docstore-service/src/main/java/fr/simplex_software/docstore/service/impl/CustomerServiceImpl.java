package fr.simplex_software.docstore.service.impl;

import co.elastic.clients.elasticsearch.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.service.*;
import jakarta.inject.*;
import jakarta.json.*;
import jakarta.json.bind.*;
import jakarta.mail.internet.*;
import org.apache.http.util.*;
import org.elasticsearch.client.*;

import java.io.*;
import java.util.*;

public class CustomerServiceImpl implements CustomerService
{
  private static final JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
  private static final Jsonb jsonb = JsonbBuilder.create();

  @Inject
  ElasticsearchClient restClient;

  @Override
  public void doIndex(Customer customer) throws IOException
  {
    Request request = new Request("PUT", "/customers/_doc/" + customer.getId());
    request.setJsonEntity(jsonb.toString());
    restClient.performRequest(request);
  }

  @Override
  public Customer getCustomer(String id) throws IOException
  {
    Request request = new Request("GET", "/customers/_doc/" + id);
    Response response = restClient.performRequest(request);
    return jsonb.fromJson(EntityUtils.toString(response.getEntity()), Customer.class);
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
    Request request = new Request("GET", "/customers/_search");
    request.setJsonEntity(getJsonQuery(term, match));
    List<Customer> customers = jsonb.fromJson(EntityUtils.toString(restClient.performRequest(request).getEntity()),
      new ArrayList<Customer>(){}.getClass().getGenericSuperclass());
    return customers;
  }

  private String getJsonQuery(String term, String match)
  {
    return jsonObjectBuilder.add("query", jsonObjectBuilder.add("match", jsonObjectBuilder.add(term, match)
      .build()).build()).toString();
  }
}
