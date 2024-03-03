package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;

import java.util.Optional;

/**
 * Gateway for retrieving and deleting messages from a queue.
 */
public interface QueueConsumer {
  Optional<QueuedProductsMessage> getMessageFromQueue() throws QueueConsumerException;

  void deleteMessageFromQueue(final QueuedProductsMessage message) throws QueueConsumerException;
}
