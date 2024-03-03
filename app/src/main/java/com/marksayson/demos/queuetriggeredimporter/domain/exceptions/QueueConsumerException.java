package com.marksayson.demos.queuetriggeredimporter.domain.exceptions;

/**
 * Exception encountered while consuming messages from a queue.
 */
public class QueueConsumerException extends Exception {
  public QueueConsumerException(final String message) {
    super(message);
  }

  public QueueConsumerException(final String message, final Throwable exceptionCause) {
    super(message, exceptionCause);
  }
}
