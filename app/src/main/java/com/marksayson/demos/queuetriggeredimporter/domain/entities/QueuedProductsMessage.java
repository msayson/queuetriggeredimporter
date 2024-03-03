package com.marksayson.demos.queuetriggeredimporter.domain.entities;

/**
 * An upstream message providing information
 * on where to import products from.
 *
 * @param sourceLocation Location to retrieve products from
 */
public class QueuedProductsMessage {
  private final String sourceLocation;

  public QueuedProductsMessage(final String sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public String getSourceLocation() {
    return sourceLocation;
  }
}
