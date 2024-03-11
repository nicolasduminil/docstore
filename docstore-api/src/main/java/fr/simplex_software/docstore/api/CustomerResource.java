package fr.simplex_software.docstore.api;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.media.*;
import org.eclipse.microprofile.openapi.annotations.parameters.*;
import org.eclipse.microprofile.openapi.annotations.responses.*;

import java.io.*;

import static jakarta.ws.rs.core.MediaType.*;

public interface CustomerResource
{
  @POST
  Response createCustomer (Customer customer, @Context UriInfo uriInfo) throws IOException;
  @Path("id")
  @GET
  Response findCustomerById (@QueryParam("id") String id) throws IOException;
  @Path("address")
  @GET
  Response findCustomerByAddress (@QueryParam("address")Address address) throws IOException;
  @Path("email")
  @GET
  Response findCustomerByEmailAddress (@QueryParam("emailAddress")String emailAddress) throws IOException;
  @GET
  Response findCustomer (@QueryParam("term")String term, @QueryParam("match")String match) throws IOException;
  @Operation(description = "Update a customer")
  @PUT
  Response updateCustomer (Customer customer) throws IOException;
  @DELETE
  Response deleteCustomerById (@QueryParam("id")String id) throws IOException;
}
