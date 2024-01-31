package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueReceiver;

import java.util.LinkedList;
import java.util.Optional;

/**
 * In-memory implementation of a queue retriever.
 */
public class InMemoryQueueReceiver implements QueueReceiver {

  private final LinkedList<QueuedRecordsMessage> queue;

  public InMemoryQueueReceiver() {
    this.queue = new LinkedList<>();
  }

  public void addMessageToQueue(final QueuedRecordsMessage message) {
    queue.add(message);
  }

  @Override
  public Optional<QueuedRecordsMessage> getMessageFromQueue() {
    if (queue.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(queue.getFirst());
  }

  @Override
  public void deleteMessageFromQueue(final QueuedRecordsMessage message) {
    queue.remove(message);
  }
}
