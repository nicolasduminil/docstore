package fr.simplex_software.docstore.service.impl;

import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import fr.simplex_software.docstore.service.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.math.*;
import java.util.*;
import java.util.stream.*;

@ApplicationScoped
public class ProductServiceImpl implements ProductService
{
  @Inject
  ProductRepository productRepository;

  @Override
  public List<Product> findAllProducts()
  {
    return productRepository.listAll();
  }

  @Override
  public Optional<Product> findProductById(Long id)
  {
    return productRepository.findByIdOptional(id);
  }

  @Override
  public List<Product> findProductsByName(String name)
  {
    return productRepository.find("name = ?1", name).list();
  }

  @Override
  public List<Product> findProductsByNameAndPrice(String name, BigDecimal price)
  {
    return productRepository.find("name = ?1 and price = ?2", name, price).list();
  }

  @Override
  public List<Product> findProductsByAttribute(String key, String value)
  {
    return productRepository.findAll().stream().filter(p -> p.getAttributes().get(key)
      .equals(value)).collect(Collectors.toList());
  }

  @Override
  public List<Product> findProductsByAttributes(HashMap<String, String> attributes)
  {
    return productRepository.findAll().stream().filter(p -> p.getAttributes().entrySet()
      .containsAll(attributes.entrySet())).collect(Collectors.toList());
  }

  @Override
  public void createProduct(Product product)
  {
    productRepository.persist(product);
  }

  @Override
  public void updateProduct(Long id, Product product)
  {
    productRepository.persistOrUpdate(new Product (id, product));
  }

  @Override
  public void removeProduct(Product product)
  {
    productRepository.delete(product);
  }
}
