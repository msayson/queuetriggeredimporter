package com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.Product;
import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryQueueReceiver;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsRetriever;

import java.util.List;

import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryProductsProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductsIngestorTest {
  private InMemoryQueueReceiver queueReceiver;
  private InMemoryProductsRetriever productsRetriever;
  private InMemoryProductsProcessor productsProcessor;
  private ProductsIngestor ingestor;

  private static QueuedProductsMessage TEST_MESSAGE = new QueuedProductsMessage("SourceLocation");

  @BeforeEach void setup() {
    queueReceiver = new InMemoryQueueReceiver();
    productsRetriever = new InMemoryProductsRetriever();
    productsProcessor = new InMemoryProductsProcessor();

    ingestor = new ProductsIngestor(queueReceiver, productsRetriever, productsProcessor);
  }

  @Test void testIngestRecordsWithEmptyQueue() {
    ingestor.ingestProducts();
  }

  @Test void testIngestRecordsDeletesProcessedMessageFromQueue() {
    queueReceiver.addMessageToQueue(TEST_MESSAGE);

    ingestor.ingestProducts();
    assertTrue(queueReceiver.getMessageFromQueue().isEmpty());
  }

  @Test void testIngestRecordsWithEmptyData() {
    queueReceiver.addMessageToQueue(TEST_MESSAGE);
    productsRetriever.uploadProducts(TEST_MESSAGE.sourceLocation(), List.of());

    ingestor.ingestProducts();
  }

  @Test void testIngestRecordsWithAvailableData() {
    final Product product = new Product(
      "id", "data", "2023-12-01T10:55:01Z", "2024-01-28T05:35:22Z"
    );
    queueReceiver.addMessageToQueue(TEST_MESSAGE);
    productsRetriever.uploadProducts(
      TEST_MESSAGE.sourceLocation(),
      List.of(product, product)
    );

    ingestor.ingestProducts();
  }
}
