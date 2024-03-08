package fr.simplex_software.docstore.api.impl;

import fr.simplex_software.docstore.api.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.service.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.spi.*;

import java.io.*;

import static jakarta.ws.rs.core.MediaType.*;

@Path("orders")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderResourceImpl implements OrderResource
{
  @Inject
  OrderService orderService;

  @Override
  public Response createOrder(Order order, UriInfo uriInfo) throws IOException
  {
    return Response.accepted(orderService.doIndex(order)).build();
  }

  @Override
  public Response findOrderById(String id) throws IOException
  {
    return Response.ok().entity(orderService.getOrder(id)).build();
  }

  @Override
  public Response findOrder(String term, String match) throws IOException
  {
    return Response.ok().entity(orderService.searchOrder(term, match)).build();
  }

  @Override
  public Response findOrderByAddress(Address address) throws IOException
  {
    return Response.ok().entity(orderService.searchOrderByAddress(address)).build();
  }

  @Override
  public Response findOrderByCustomerId(String customerId) throws IOException
  {
    return Response.ok().entity(orderService.searchOrderByCustomerId(customerId)).build();
  }

  @Override
  public Response updateOrder(Order order) throws IOException
  {
    orderService.modifyOrder(order);
    return Response.noContent().build();
  }

  @Override
  public Response deleteOrderById(String id) throws IOException
  {
    orderService.removeOrderById(id);
    return Response.noContent().build();
  }
}
