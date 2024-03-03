package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueConsumer;
import jakarta.jms.MessageConsumer;

import java.util.Optional;

/**
 * AWS Simple Queue Service (SQS) implementation of a queue retriever.
 */
public class SQSQueueConsumer implements QueueConsumer {

  private final MessageConsumer messageConsumer;

  public SQSQueueConsumer(final MessageConsumer messageConsumer) {
    this.messageConsumer = messageConsumer;
  }

  @Override
  public Optional<QueuedProductsMessage> getMessageFromQueue() {
    return Optional.empty();
  }

  @Override
  public void deleteMessageFromQueue(final QueuedProductsMessage message) {
  }
}
