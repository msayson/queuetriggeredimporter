package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InMemoryQueueReceiverTest {
  private InMemoryQueueReceiver receiver;

  private static QueuedProductsMessage TEST_MESSAGE = new QueuedProductsMessage("SourceLocation");

  @BeforeEach void setup() {
    receiver = new InMemoryQueueReceiver();
  }

  @Test void testGetMessageFromEmptyQueue() {
    final Optional<QueuedProductsMessage> optionalMessage = receiver.getMessageFromQueue();
    assertTrue(optionalMessage.isEmpty());
  }

  @Test void testGetMessageFromNonEmptyQueue() {
    receiver.addMessageToQueue(TEST_MESSAGE);

    final Optional<QueuedProductsMessage> optionalMessage = receiver.getMessageFromQueue();
    assertEquals(TEST_MESSAGE.sourceLocation(), optionalMessage.get().sourceLocation());
  }

  @Test void testDeleteMessageFromEmptyQueue() {
    receiver.deleteMessageFromQueue(null);
    receiver.deleteMessageFromQueue(TEST_MESSAGE);

    assertTrue(receiver.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMessageFromNonEmptyQueue() {
    receiver.addMessageToQueue(TEST_MESSAGE);
    assertFalse(receiver.getMessageFromQueue().isEmpty());

    receiver.deleteMessageFromQueue(TEST_MESSAGE);
    assertTrue(receiver.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMessageNotInQueue() {
    receiver.addMessageToQueue(TEST_MESSAGE);
    final QueuedProductsMessage messageNotInQueue = new QueuedProductsMessage("NotInQueue");

    receiver.deleteMessageFromQueue(messageNotInQueue);
    assertFalse(receiver.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMultipleMessages() {
    final QueuedProductsMessage testMessage1 = new QueuedProductsMessage("TestMessage1");
    final QueuedProductsMessage testMessage2 = new QueuedProductsMessage("TestMessage2");
    final QueuedProductsMessage testMessage3 = new QueuedProductsMessage("TestMessage3");

    // Add out of order
    receiver.addMessageToQueue(testMessage2);
    receiver.addMessageToQueue(testMessage1);
    receiver.addMessageToQueue(testMessage3);

    // Delete all messages
    List.of(testMessage1, testMessage2, testMessage3).forEach((final QueuedProductsMessage message) -> {
      receiver.deleteMessageFromQueue(message);
    });

    assertTrue(receiver.getMessageFromQueue().isEmpty());
  }
}
