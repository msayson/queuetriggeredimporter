package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import jakarta.jms.MessageConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class SQSQueueConsumerTest {
  private MessageConsumer messageConsumer;

  private SQSQueueConsumer queueConsumer;

  private static QueuedProductsMessage TEST_MESSAGE = new QueuedProductsMessage("SourceLocation");

  @BeforeEach void setup() {
    messageConsumer = Mockito.mock(MessageConsumer.class);
    queueConsumer = new SQSQueueConsumer(messageConsumer);
  }

  @Test void testGetMessageFromEmptyQueue() {
    final Optional<QueuedProductsMessage> optionalMessage = queueConsumer.getMessageFromQueue();
    assertTrue(optionalMessage.isEmpty());
  }

  @Test void testDeleteMessageFromEmptyQueue() {
    queueConsumer.deleteMessageFromQueue(null);
    queueConsumer.deleteMessageFromQueue(TEST_MESSAGE);

    assertTrue(queueConsumer.getMessageFromQueue().isEmpty());
  }
}
