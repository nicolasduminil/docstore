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

@Path("products")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ProductResourceImpl implements ProductResource
{
  @Inject
  ProductService productService;

  @Override
  public Response createProduct(Product product, UriInfo uriInfo) throws IOException
  {
    return Response.accepted(productService.doIndex(product)).build();
  }

  @Override
  public Response findProductById(String id) throws IOException
  {
    return Response.ok().entity(productService.getProduct(id)).build();
  }

  @Override
  public Response findProduct(String term, String match) throws IOException
  {
    return Response.ok().entity(productService.searchProduct(term, match)).build();
  }

  @Override
  public Response findProductByAddress(Address address)
  {
    return Response.ok().entity(productService.searchProductByAddress(address)).build();
  }

  @Override
  public Response findProductByName(String name)
  {
    return Response.ok().entity(productService.searchProductByName(name)).build();
  }

  @Override
  public Response updateProduct(Product product) throws IOException
  {
    productService.modifyProduct(product);
    return Response.noContent().build();
  }

  @Override
  public Response deleteProductById(String id) throws IOException
  {
    productService.removeProductById(id);
    return Response.noContent().build();
  }
}
