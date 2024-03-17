package com.marksayson.demos.queuetriggeredimporter;

import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;
import com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest.ProductsIngestor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsProcessor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsRetriever;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryQueueConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Program which consumes an import notification, retrieves associated products, and processes them.
 */
public class Program {
  private static Logger logger = LoggerFactory.getLogger(Program.class);

  /**
   * Entry point for the program, instantiates infrastructure and ingests products.
   * @param args Input arguments
   */
  public static void main(final String[] args) {
    logger.info("Initializing ingestor...");
    final ProductsIngestor ingestor = instantiateIngestor();
    logger.info("Pulling available products...");
    try {
      ingestor.ingestProducts();
    } catch (final QueueConsumerException e) {
      logger.error("Unexpected QueueConsumerException while ingesting products", e);
      e.printStackTrace();
    }
    logger.info("Ingestion complete, shutting down...");
  }

  private static ProductsIngestor instantiateIngestor() {
    return new ProductsIngestor(
      new InMemoryQueueConsumer(),
      new InMemoryProductsRetriever(),
      new InMemoryProductsProcessor()
    );
  }
}
