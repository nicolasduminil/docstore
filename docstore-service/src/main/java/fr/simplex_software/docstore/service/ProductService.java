package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.util.*;

public interface ProductService
{
  void doIndex (Product product);
  Product getProduct (String id);
  List<Product> searchProductByAddress (Address address);
  List<Product> searchProductByName (String name);
  List<Product> searchProduct (String term, String match);
}
