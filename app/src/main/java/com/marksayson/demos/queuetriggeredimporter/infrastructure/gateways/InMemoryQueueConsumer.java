package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueConsumer;

import java.util.LinkedList;
import java.util.Optional;

/**
 * In-memory implementation of a queue consumer.
 */
public class InMemoryQueueConsumer implements QueueConsumer {

  private final LinkedList<QueuedProductsMessage> queue;

  public InMemoryQueueConsumer() {
    this.queue = new LinkedList<>();
  }

  public void addMessageToQueue(final QueuedProductsMessage message) {
    queue.add(message);
  }

  @Override
  public Optional<QueuedProductsMessage> getMessageFromQueue() {
    if (queue.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(queue.getFirst());
  }

  @Override
  public void deleteMessageFromQueue(final QueuedProductsMessage message) {
    queue.remove(message);
  }
}
