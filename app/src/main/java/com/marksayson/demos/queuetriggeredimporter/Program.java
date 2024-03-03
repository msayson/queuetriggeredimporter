package com.marksayson.demos.queuetriggeredimporter;

import com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest.ProductsIngestor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsProcessor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsRetriever;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryQueueConsumer;

/**
 * Program which consumes an import notification, retrieves associated products, and processes them.
 */
public class Program {
  /**
   * Entry point for the program, instantiates infrastructure and ingests products.
   * @param args Input arguments
   */
  public static void main(final String[] args) {
    System.out.println("Initializing ingestor...");
    final ProductsIngestor ingestor = instantiateIngestor();
    System.out.println("Pulling available products...");
    ingestor.ingestProducts();
    System.out.println("Ingestion complete, shutting down...");
  }

  private static ProductsIngestor instantiateIngestor() {
    return new ProductsIngestor(
      new InMemoryQueueConsumer(),
      new InMemoryProductsRetriever(),
      new InMemoryProductsProcessor()
    );
  }
}
