package com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;

/**
 * In-memory implementation of a queued products message.
 */
public class InMemoryQueuedProductsMessage implements QueuedProductsMessage {
  private String productsSourceLocation;

  public InMemoryQueuedProductsMessage(final String productsSourceLocation) {
    this.productsSourceLocation = productsSourceLocation;
  }

  /**
   * Retrieve source location.
   */
  @Override
  public String getSourceLocation() {
    return productsSourceLocation;
  }

  /**
   * No-op implementation of acknowledging an in-memory queue message as processed.
   */
  @Override
  public void acknowledge() throws QueueConsumerException {
    return;
  }
}
