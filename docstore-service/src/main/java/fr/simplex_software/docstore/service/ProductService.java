package fr.simplex_software.docstore.service;

import fr.simplex_software.docstore.domain.*;

import java.io.*;
import java.util.*;

public interface ProductService
{
  String doIndex (Product product) throws IOException;
  Product getProduct (String id) throws IOException;
  List<Product> searchProductByAddress (Address address);
  List<Product> searchProductByName (String name);
  List<Product> searchProduct (String term, String match) throws IOException;
}
