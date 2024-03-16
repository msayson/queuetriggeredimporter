package com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * Implementation of a SQS pending products message.
 */
public class SQSPendingProductsMessage implements QueuedProductsMessage {
  private Message message;
  private String productsSourceLocation;

  public SQSPendingProductsMessage(final Message message, final String productsSourceLocation) {
    this.message = message;
    this.productsSourceLocation = productsSourceLocation;
  }

  /**
   * Retrieve source location of products pending import.
   */
  @Override
  public String getSourceLocation() {
    return productsSourceLocation;
  }

  /**
   * Delete source message from the SQS queue.
   */
  @Override
  public void acknowledge() throws QueueConsumerException {
    try {
      message.acknowledge();
    } catch (final JMSException e) {
      throw new QueueConsumerException("Unexpected error deleting message from queue", e);
    }
  }
}
