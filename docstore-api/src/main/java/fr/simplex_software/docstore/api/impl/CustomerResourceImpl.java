package fr.simplex_software.docstore.api.impl;

import fr.simplex_software.docstore.api.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.service.*;
import fr.simplex_software.docstore.service.impl.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.spi.*;

import java.io.*;

import static jakarta.ws.rs.core.MediaType.*;

@Path("customers")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class CustomerResourceImpl implements CustomerResource
{
  @Inject
  CustomerService customerService;

  @Override
  public Response createCustomer(Customer customer, @Context UriInfo uriInfo) throws IOException
  {
    return Response.created(uriInfo.getAbsolutePathBuilder().path(customerService.doIndex(customer)).build()).build();
  }

  @Override
  public Response findCustomerById(String id) throws IOException
  {
    return Response.ok().entity(customerService.getCustomer(id)).build();
  }

  @Override
  public Response findCustomerByAddress(Address address) throws IOException
  {
    return Response.ok().entity(customerService.searchCustomerByAddress(address)).build();
  }

  @Override
  public Response findCustomerByEmailAddress(String emailAddress) throws IOException
  {
    return Response.ok().entity(emailAddress).build();
  }

  @Override
  public Response findCustomer(String term, String match) throws IOException
  {
    return Response.ok().entity(customerService.searchCustomer(term, match)).build();
  }

  @Override
  public Response updateCustomer(Customer customer)
  {
    throw new NotImplementedYetException("### CustomerResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }

  @Override
  public Response deleteCustomer(Customer customer)
  {
    throw new NotImplementedYetException("### CustomerResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }
}
