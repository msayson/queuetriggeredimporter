package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.ProductsRetriever;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of a products retriever.
 */
public class InMemoryProductsRetriever implements ProductsRetriever {

  private final Map<String, Collection<Product>> dataProvider;

  public InMemoryProductsRetriever() {
    dataProvider = new HashMap<>();
  }

  public void uploadProducts(final String sourceLocation, final Collection<Product> products) {
    dataProvider.put(sourceLocation, products);
  }

  @Override
  public Optional<Collection<Product>> retrieveProducts(final String sourceLocation) {
    final Collection<Product> nullableMatchingProducts = dataProvider.get(sourceLocation);
    return Optional.ofNullable(nullableMatchingProducts);
  }
}
