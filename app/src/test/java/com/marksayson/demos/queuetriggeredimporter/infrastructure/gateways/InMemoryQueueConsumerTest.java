package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters.InMemoryQueuedProductsMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InMemoryQueueConsumerTest {
  private InMemoryQueueConsumer queueConsumer;

  private static QueuedProductsMessage TEST_MESSAGE = new InMemoryQueuedProductsMessage("SourceLocation");

  @BeforeEach void setup() {
    queueConsumer = new InMemoryQueueConsumer();
  }

  @Test void testGetMessageFromEmptyQueue() {
    final Optional<QueuedProductsMessage> optionalMessage = queueConsumer.getMessageFromQueue();
    assertTrue(optionalMessage.isEmpty());
  }

  @Test void testGetMessageFromNonEmptyQueue() {
    queueConsumer.addMessageToQueue(TEST_MESSAGE);

    final Optional<QueuedProductsMessage> optionalMessage = queueConsumer.getMessageFromQueue();
    assertEquals(TEST_MESSAGE.getSourceLocation(), optionalMessage.get().getSourceLocation());
  }

  @Test void testDeleteMessageFromEmptyQueue() {
    queueConsumer.deleteMessageFromQueue(null);
    queueConsumer.deleteMessageFromQueue(TEST_MESSAGE);

    assertTrue(queueConsumer.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMessageFromNonEmptyQueue() {
    queueConsumer.addMessageToQueue(TEST_MESSAGE);
    assertFalse(queueConsumer.getMessageFromQueue().isEmpty());

    queueConsumer.deleteMessageFromQueue(TEST_MESSAGE);
    assertTrue(queueConsumer.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMessageNotInQueue() {
    queueConsumer.addMessageToQueue(TEST_MESSAGE);
    final QueuedProductsMessage messageNotInQueue = new InMemoryQueuedProductsMessage("NotInQueue");

    queueConsumer.deleteMessageFromQueue(messageNotInQueue);
    assertFalse(queueConsumer.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMultipleMessages() {
    final QueuedProductsMessage testMessage1 = new InMemoryQueuedProductsMessage("TestMessage1");
    final QueuedProductsMessage testMessage2 = new InMemoryQueuedProductsMessage("TestMessage2");
    final QueuedProductsMessage testMessage3 = new InMemoryQueuedProductsMessage("TestMessage3");

    // Add out of order
    queueConsumer.addMessageToQueue(testMessage2);
    queueConsumer.addMessageToQueue(testMessage1);
    queueConsumer.addMessageToQueue(testMessage3);

    // Delete all messages
    List.of(testMessage1, testMessage2, testMessage3).forEach((final QueuedProductsMessage message) -> {
      queueConsumer.deleteMessageFromQueue(message);
    });

    assertTrue(queueConsumer.getMessageFromQueue().isEmpty());
  }
}
