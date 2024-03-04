package fr.simplex_software.docstore.api;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.*;

public interface OrderResource
{
  @POST
  Response createOrder (Order order, @Context UriInfo uriInfo) throws IOException;
  @Path("id")
  @GET
  Response findOrderById (@QueryParam("id") String id) throws IOException;
  @GET
  Response findOrder (@QueryParam("term")String term, @QueryParam("match")String match) throws IOException;
  @Path("address")
  @GET
  Response findOrderByAddress (@QueryParam("address")Address address) throws IOException;
  @Path("customer-id")
  @GET
  Response findOrderByCustomerId (@QueryParam("customer-id")String customerId) throws IOException;
  @PUT
  Response updateOrder (Order order);
  @DELETE
  Response deleteOrder (OrderItem order);
}
