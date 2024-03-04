package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters.SQSPendingProductsMessage;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class SQSQueueConsumerTest {
  private MessageConsumer messageConsumer;

  private SQSQueueConsumer queueConsumer;

  private static String TEST_SOURCE_LOCATION = "TestSourceLocation";
  private static QueuedProductsMessage TEST_MESSAGE = new QueuedProductsMessage(TEST_SOURCE_LOCATION);

  @BeforeEach void setup() {
    messageConsumer = mock(MessageConsumer.class);
    queueConsumer = new SQSQueueConsumer(messageConsumer);
  }

  @Test void testGetMessageFromEmptyQueue() throws Exception {
    final Optional<QueuedProductsMessage> optionalMessage = queueConsumer.getMessageFromQueue();
    assertTrue(optionalMessage.isEmpty());
  }

  @Test void testGetValidMessage() throws Exception {
    final TextMessage mockMessage = mock(TextMessage.class);
    when(mockMessage.getText()).thenReturn(TEST_SOURCE_LOCATION);
    when(messageConsumer.receive(anyLong())).thenReturn(mockMessage);

    final Optional<QueuedProductsMessage> optionalMessage = queueConsumer.getMessageFromQueue();
    assertTrue(optionalMessage.isPresent());
    assertEquals(TEST_SOURCE_LOCATION, optionalMessage.get().getSourceLocation());
  }

  @Test void testGetInvalidMessage() throws Exception {
    final Message mockMessage = mock(Message.class);
    when(messageConsumer.receive(anyLong())).thenReturn(mockMessage);

    final QueueConsumerException thrownException = assertThrows(QueueConsumerException.class, () -> { queueConsumer.getMessageFromQueue(); });
    assertTrue(thrownException.getMessage().contains("Unexpected message type"));
  }

  @Test void testGetMessageWhenQueueReceiveFails() throws Exception {
    when(messageConsumer.receive(anyLong())).thenThrow(new JMSException("Error connecting to queue"));

    final QueueConsumerException thrownException = assertThrows(QueueConsumerException.class, () -> { queueConsumer.getMessageFromQueue(); });
    assertEquals("Unexpected error retrieving message from queue", thrownException.getMessage());
  }

  @Test void testDeleteUnsupportedMessage() throws Exception {
    queueConsumer.deleteMessageFromQueue(null);
    queueConsumer.deleteMessageFromQueue(TEST_MESSAGE);
  }

  @Test void testDeletePendingProductsMessage() throws Exception {
    final Message mockSQSMessage = mock(Message.class);
    doNothing().when(mockSQSMessage).acknowledge();

    final SQSPendingProductsMessage pendingProductsMessage = new SQSPendingProductsMessage(mockSQSMessage, TEST_SOURCE_LOCATION);
    queueConsumer.deleteMessageFromQueue(pendingProductsMessage);

    verify(mockSQSMessage).acknowledge();
  }

  @Test void testAcknowledgeMessageFailure() throws Exception {
    final Message mockSQSMessage = mock(Message.class);
    doThrow(new JMSException("TestException")).when(mockSQSMessage).acknowledge();

    final SQSPendingProductsMessage pendingProductsMessage = new SQSPendingProductsMessage(mockSQSMessage, TEST_SOURCE_LOCATION);
    final QueueConsumerException thrownException = assertThrows(QueueConsumerException.class, () -> { queueConsumer.deleteMessageFromQueue(pendingProductsMessage); });
    assertEquals("Unexpected error deleting message from queue", thrownException.getMessage());
  }
}
