package fr.simplex_software.docstore.api;

import fr.simplex_software.docstore.domain.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.*;

public interface ProductResource
{
  @POST
  Response createProduct (Product product, @Context UriInfo uriInfo) throws IOException;
  @Path("id")
  @GET
  Response findProductById (@QueryParam("id") String id) throws IOException;
  @GET
  Response findProduct (@QueryParam("term")String term, @QueryParam("match")String match) throws IOException;
  @Path("address")
  @GET
  Response findProductByAddress (@QueryParam("address")Address address);
  @Path("name")
  @GET
  Response findProductByName (@QueryParam("name")String name);
  @PUT
  Response updateProduct (Product product) throws IOException;
  @DELETE
  Response deleteProductById (@QueryParam("id")String id) throws IOException;
}
