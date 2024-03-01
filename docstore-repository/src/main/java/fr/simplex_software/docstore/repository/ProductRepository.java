package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;

import java.util.*;

public interface ProductRepository
{
  void index (Product product);
  Product get (String id);
  List<Product> searchByAddress (Address address);
  List<Product> searchByName (String name);
  List<Product> search (String term, String match);
}
