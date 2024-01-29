package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InMemoryQueueReceiverTest {
  private InMemoryQueueReceiver receiver;

  private static QueuedRecordsMessage TEST_MESSAGE = new QueuedRecordsMessage("TestRecordSource");

  @BeforeEach void setup() {
    receiver = new InMemoryQueueReceiver();
  }

  @Test void testGetMessageFromEmptyQueue() {
    final Optional<QueuedRecordsMessage> optionalMessage = receiver.getMessageFromQueue();
    assertTrue(optionalMessage.isEmpty());
  }

  @Test void testGetMessageFromNonEmptyQueue() {
    receiver.addMessageToQueue(TEST_MESSAGE);

    final Optional<QueuedRecordsMessage> optionalMessage = receiver.getMessageFromQueue();
    assertEquals(TEST_MESSAGE.recordsSource(), optionalMessage.get().recordsSource());
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
    final QueuedRecordsMessage messageNotInQueue = new QueuedRecordsMessage("NotInQueue");

    receiver.deleteMessageFromQueue(messageNotInQueue);
    assertFalse(receiver.getMessageFromQueue().isEmpty());
  }

  @Test void testDeleteMultipleMessages() {
    final QueuedRecordsMessage testMessage1 = new QueuedRecordsMessage("TestMessage1");
    final QueuedRecordsMessage testMessage2 = new QueuedRecordsMessage("TestMessage2");
    final QueuedRecordsMessage testMessage3 = new QueuedRecordsMessage("TestMessage3");

    // Add out of order
    receiver.addMessageToQueue(testMessage2);
    receiver.addMessageToQueue(testMessage1);
    receiver.addMessageToQueue(testMessage3);

    // Delete all messages
    List.of(testMessage1, testMessage2, testMessage3).forEach((final QueuedRecordsMessage message) -> {
      receiver.deleteMessageFromQueue(message);
    });

    assertTrue(receiver.getMessageFromQueue().isEmpty());
  }
}
