package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.math.*;
import java.util.*;

public interface ProductService
{
  List<Product> findAllProducts();
  Optional<Product> findProductById (Long id);
  List<Product> findProductsByName (String name);
  List<Product> findProductsByNameAndPrice (String name, BigDecimal price);
  List<Product> findProductsByAttribute (String key, String value);
  List<Product> findProductsByAttributes (HashMap<String, String> attributes);
  void createProduct (Product product);
  void updateProduct (Long id, Product product);
  void removeProduct (Product product);
}
