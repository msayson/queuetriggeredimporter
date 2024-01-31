package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;

import java.util.Collection;
import java.util.Optional;

/**
 * Gateway for retrieving products from a data source.
 */
public interface ProductsRetriever {
  Optional<Collection<Product>> retrieveProducts(final String sourceLocation);
}
