package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedProductsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueConsumer;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.adapters.SQSPendingProductsMessage;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * AWS Simple Queue Service (SQS) implementation of a queue retriever.
 *
 * Assumption for this demo: the SQS message body is the exact string
 * representing the location of the source products to ingest.  Update
 * the parsing logic if the message has a different structure.
 */
public class SQSQueueConsumer implements QueueConsumer {

  /**
   * Time to wait to receive messages from the queue.
   * Must be less or equal to the SQS queue's configured visibility timeout
   * to prevent other queue consumers from processing the same messages.
   * Ref: https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-visibility-timeout.html
   */
  private static final long SQS_QUERY_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(3);

  private final MessageConsumer messageConsumer;

  public SQSQueueConsumer(final MessageConsumer messageConsumer) {
    this.messageConsumer = messageConsumer;
  }

  @Override
  public Optional<QueuedProductsMessage> getMessageFromQueue() throws QueueConsumerException {
    try {
      final Optional<Message> optionalMessage = Optional.ofNullable(messageConsumer.receive(SQS_QUERY_TIMEOUT_MILLIS));
      if (optionalMessage.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(parseFromMessage(optionalMessage.get()));
    } catch (final JMSException e) {
      throw new QueueConsumerException("Unexpected error retrieving message from queue", e);
    }
  }

  @Override
  public void deleteMessageFromQueue(final QueuedProductsMessage message) throws QueueConsumerException {
    if (message instanceof SQSPendingProductsMessage) {
      ((SQSPendingProductsMessage) message).acknowledge();
    }
  }

  private SQSPendingProductsMessage parseFromMessage(final Message message) throws QueueConsumerException, JMSException {
    if (!(message instanceof TextMessage)) {
      throw new QueueConsumerException(String.format(
        "Unexpected message type, failed to parse message %s: %s",
        message.getJMSMessageID(),
        message.toString())
      );
    }
    final String productsSourceLocation = ((TextMessage) message).getText();
    return new SQSPendingProductsMessage(message, productsSourceLocation);
  }
}
