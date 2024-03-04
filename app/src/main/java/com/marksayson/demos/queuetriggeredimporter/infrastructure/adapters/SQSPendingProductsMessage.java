package com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * Implementation of a SQS pending products message.
 */
public class SQSPendingProductsMessage extends QueuedProductsMessage {
  private Message message;

  public SQSPendingProductsMessage(final Message message, final String productsSourceLocation) {
    super(productsSourceLocation);
    this.message = message;
  }

  /**
   * Delete source message from the SQS queue.
   */
  public void acknowledge() throws QueueConsumerException {
    try {
      message.acknowledge();
    } catch (final JMSException e) {
      throw new QueueConsumerException("Unexpected error deleting message from queue", e);
    }
  }
}
