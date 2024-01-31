package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InMemoryProductsRetrieverTest {
  private InMemoryProductsRetriever retriever;

  private static final String TEST_SOURCE_LOCATION = "TestSourceDataLocation";
  private static final String VALID_DATETIME = "2011-12-03T10:15:30Z";
  private static final Collection<Product> TEST_PRODUCTS = List.of(
    new Product("1", "title1", VALID_DATETIME, VALID_DATETIME),
    new Product("2", "title2", VALID_DATETIME, VALID_DATETIME)
  );

  @BeforeEach void setup() {
    retriever = new InMemoryProductsRetriever();
  }

  @Test void testRetrieveNonExistingProducts() {
    final String locationWithoutProducts = "NoProductsHere";
    assertTrue(retriever.retrieveProducts(locationWithoutProducts).isEmpty());

    retriever.uploadProducts(TEST_SOURCE_LOCATION, TEST_PRODUCTS);
    assertTrue(retriever.retrieveProducts(locationWithoutProducts).isEmpty());
  }

  @Test void testRetrieveExistingProducts() {
    retriever.uploadProducts(TEST_SOURCE_LOCATION, TEST_PRODUCTS);

    final Optional<Collection<Product>> products = retriever.retrieveProducts(TEST_SOURCE_LOCATION);
    assertTrue(products.isPresent());
    assertEquals(TEST_PRODUCTS, products.get());
  }
}
