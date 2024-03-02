package fr.simplex_software.docstore.api.impl;

import fr.simplex_software.docstore.api.*;
import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.service.*;
import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.jboss.resteasy.spi.*;

import java.io.*;

@Path("orders")
public class OrderResourceImpl implements OrderResource
{
  @Inject
  OrderService orderService;

  @Override
  public Response createOrder(Order order, UriInfo uriInfo) throws IOException
  {
    return Response.created(uriInfo.getAbsolutePathBuilder().path(orderService.doIndex(order)).build()).build();
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
  public Response findOrderByAddress(Address address)
  {
    return Response.ok().entity(orderService.searchOrderByAddress(address)).build();
  }

  @Override
  public Response findOrderByCustomerId(String customerId)
  {
    return Response.ok().entity(orderService.searchOrderByCustomerId(customerId)).build();
  }

  @Override
  public Response updateOrder(Order order)
  {
    throw new NotImplementedYetException("### OrderResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }

  @Override
  public Response deleteOrder(OrderItem order)
  {
    throw new NotImplementedYetException("### OrderResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }
}
