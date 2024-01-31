package com.marksayson.demos.queuetriggeredimporter.domain.entities;

/**
 * An upstream message providing information
 * on where to import products from.
 *
 * @param sourceLocation Location to retrieve products from
 */
public record QueuedProductsMessage(
  String sourceLocation
) {}
