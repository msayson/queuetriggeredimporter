package com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;
import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.ProductsProcessor;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.ProductsRetriever;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueConsumer;

import java.util.Collection;
import java.util.Optional;

/**
 * Encapsulates logic for ingesting products based on queued data import messages.
 */
public class ProductsIngestor {
  private final QueueConsumer queueReceiver;
  private final ProductsRetriever productsRetriever;
  private final ProductsProcessor productsProcessor;

  /**
   * Construct instance of ProductsIngestor.
   * @param queueReceiver    Data import queue listener
   * @param productsRetriever Products retriever
   * @param productsProcessor Products processor
   */
  public ProductsIngestor(
    final QueueConsumer queueReceiver,
    final ProductsRetriever productsRetriever,
    final ProductsProcessor productsProcessor
  ) {
    this.queueReceiver = queueReceiver;
    this.productsRetriever = productsRetriever;
    this.productsProcessor = productsProcessor;
  }

  /**
   * Ingest products from the data source provided by the latest message in the queue.
   */
  public void ingestProducts() {
    final Optional<QueuedProductsMessage> optionalMessage = queueReceiver.getMessageFromQueue();
    if (optionalMessage.isEmpty()) {
      return;
    }

    final QueuedProductsMessage message = optionalMessage.get();
    final String sourceLocation = message.sourceLocation();
    final Optional<Collection<Product>> optionalProducts = productsRetriever.retrieveProducts(sourceLocation);
    if (optionalProducts.isPresent()) {
      productsProcessor.processProducts(optionalProducts.get());
    }
    queueReceiver.deleteMessageFromQueue(message);
  }
}
