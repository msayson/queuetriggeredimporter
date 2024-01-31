package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.ProductsProcessor;

import java.util.Collection;

/**
 * In-memory implementation of a products processor.
 */
public class InMemoryProductsProcessor implements ProductsProcessor {

  @Override
  public void processProducts(final Collection<Product> products) {
    return;
  }
}
