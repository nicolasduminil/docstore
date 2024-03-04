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
  @Operation(description = "Create a new customer")
  @APIResponse(responseCode = "500", description = "An internal server error has occurred",
    content = @Content(mediaType = APPLICATION_XML))
  @APIResponseSchema(value = Customer.class, responseDescription = "The new customer has been created", responseCode = "201")
  Response createCustomer (@RequestBody (content = @Content (example = "{\n" +
    "  \"id\": 10,\n" +
    "  \"firstName\": \"John\",\n" +
    "  \"lastName\": \"Doe\",\n" +
    "  \"email\": {\n" +
    "    \"address\": \"john.doe@gmail.com\",\n" +
    "    \"personal\": \"John Doe\",\n" +
    "    \"encodedPersonal\": \"John Doe\",\n" +
    "    \"type\": \"personal\",\n" +
    "    \"simple\": true,\n" +
    "    \"group\": true\n" +
    "  },\n" +
    "  \"addresses\": [\n" +
    "    {\n" +
    "      \"street\": \"75, rue Véronique Coulon\",\n" +
    "      \"city\": \"Coste\",\n" +
    "      \"country\": \"France\"\n" +
    "    },\n" +
    "    {\n" +
    "      \"street\": \"Wulfweg 827\",\n" +
    "      \"city\": \"Bautzen\",\n" +
    "      \"country\": \"Germany\"\n" +
    "    }\n" +
    "  ]\n" +
    "}")) Customer customer, @Context UriInfo uriInfo) throws IOException;
  @Path("id")
  @GET
  @Operation(description = "Find a customer by its ID")
  @APIResponse(responseCode = "404", description = "No customer found for the given ID",
    content = @Content(mediaType = APPLICATION_JSON))
  @APIResponseSchema(value = Customer.class, responseDescription = "The customer having the given ID", responseCode = "200")
  Response findCustomerById (@QueryParam("id") String id) throws IOException;
  @Path("address")
  @GET
  @Operation(description = "Find a customer by its address")
  @APIResponse(responseCode = "404", description = "No customer found for the given address",
    content = @Content(mediaType = APPLICATION_JSON))
  @APIResponseSchema(value = Customer.class, responseDescription = "The customer having the given address", responseCode = "200")
  Response findCustomerByAddress (@QueryParam("address")Address address) throws IOException;
  @Path("email")
  @GET
  @Operation(description = "Find a customer by its email address")
  @APIResponse(responseCode = "404", description = "No customer found for the given email address",
    content = @Content(mediaType = APPLICATION_JSON))
  @APIResponseSchema(value = Customer.class, responseDescription = "The customer having the given email address", responseCode = "200")
  Response findCustomerByEmailAddress (@QueryParam("emailAddress")String emailAddress) throws IOException;
  @GET
  @Operation(description = "Find a customer which \"term\" parameter matches the \"match\" parameter")
  @APIResponse(responseCode = "404", description = "No customer found for the given term/match couple",
    content = @Content(mediaType = APPLICATION_JSON))
  @APIResponseSchema(value = Customer.class, responseDescription = "The customer satisfying the given match", responseCode = "200")
  Response findCustomer (@QueryParam("term")String term, @QueryParam("match")String match) throws IOException;
  @Operation(description = "Update a customer")
  @APIResponse(responseCode = "404", description = "No such customer",
    content = @Content(mediaType = APPLICATION_JSON))
  @APIResponseSchema(value = Customer.class, responseDescription = "The updated customer", responseCode = "200")
  @PUT
  Response updateCustomer (Customer customer);
  @DELETE
  @Operation(description = "Delete a customer")
  @APIResponse(responseCode = "404", description = "No such customer",
    content = @Content(mediaType = APPLICATION_JSON))
  Response deleteCustomer (Customer customer);
}
