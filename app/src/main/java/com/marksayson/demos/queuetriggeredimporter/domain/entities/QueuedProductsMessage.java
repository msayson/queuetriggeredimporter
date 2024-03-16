package com.marksayson.demos.queuetriggeredimporter.domain.entities;

import com.marksayson.demos.queuetriggeredimporter.domain.exceptions.QueueConsumerException;

/**
 * An upstream message providing information
 * on where to import products from.
 */
public interface QueuedProductsMessage {
  /**
   * Retrieve source location of products queued for import
   * from the message.
   */
  public String getSourceLocation();

  /**
   * Acknowledge message to indicate it has been successfully processed
   * and can be cleared from the queue.
   */
  public void acknowledge() throws QueueConsumerException;
}
