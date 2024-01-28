package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;

import java.util.Optional;

/**
 * Gateway for retrieving and deleting messages from a queue.
 */
public interface QueueReceiver {
  Optional<QueuedRecordsMessage> getMessageFromQueue();

  void deleteMessageFromQueue(final QueuedRecordsMessage message);
}
