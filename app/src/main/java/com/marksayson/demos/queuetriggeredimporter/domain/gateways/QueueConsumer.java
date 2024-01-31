package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;

import java.util.Optional;

/**
 * Gateway for retrieving and deleting messages from a queue.
 */
public interface QueueConsumer {
  Optional<QueuedProductsMessage> getMessageFromQueue();

  void deleteMessageFromQueue(final QueuedProductsMessage message);
}
