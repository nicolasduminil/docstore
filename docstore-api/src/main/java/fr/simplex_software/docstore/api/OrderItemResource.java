package fr.simplex_software.docstore.api;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.*;

public interface OrderItemResource
{
  @POST
  Response createOrderItem (OrderItem orderItem, @Context UriInfo uriInfo) throws IOException;
  @Path("id")
  @GET
  Response findOrderItemById (@QueryParam("id") String id) throws IOException;
  @Path("product-id")
  @GET
  Response findOrderItemByProductId (@QueryParam("productId")String productId) throws IOException;
  @GET
  Response findOrderItem (@QueryParam("term")String term, @QueryParam("match")String match) throws IOException;
  @PUT
  Response updateOrderItem (OrderItem orderItem) throws IOException;
  @DELETE
  Response deleteOrderItemById (String id) throws IOException;
}
