package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;

import java.util.Collection;

/**
 * Gateway for processing incoming products.
 */
public interface ProductsProcessor {
  void processProducts(final Collection<Product> product);
}
