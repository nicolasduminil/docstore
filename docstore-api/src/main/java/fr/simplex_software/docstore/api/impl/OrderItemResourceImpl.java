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

@Path("order-items")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class OrderItemResourceImpl implements OrderItemResource
{
  @Inject
  OrderItemService orderItemService;

  @Override
  public Response createOrderItem(OrderItem orderItem, UriInfo uriInfo) throws IOException
  {
    return Response.created(uriInfo.getAbsolutePathBuilder().path(orderItemService.doIndex(orderItem)).build()).build();
  }

  @Override
  public Response findOrderItemById(String id) throws IOException
  {
    return Response.ok().entity(orderItemService.getOrderItem(id)).build();
  }

  @Override
  public Response findOrderItemByProductId(String productId) throws IOException
  {
    return Response.ok().entity(orderItemService.searchOrderItemByProductId(productId)).build();
  }

  @Override
  public Response findOrderItem(String term, String match) throws IOException
  {
    return Response.ok().entity(orderItemService.searchOrderItem(term, match)).build();
  }

  @Override
  public Response updateOrderItem(OrderItem orderItem)
  {
    throw new NotImplementedYetException("### OrderItemResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }

  @Override
  public Response deleteOrderItem(OrderItem orderItem)
  {
    throw new NotImplementedYetException("### OrderItemResourceImpl.updateCustomer(): To be implemented by reader as an exercise");
  }
}
