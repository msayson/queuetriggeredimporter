package com.marksayson.demos.queuetriggeredimporter.domain.entities;

/**
 * An upstream message providing information
 * on where to import records from.
 * 
 * @param recordsSource Location to retrieve records from
 */
public record QueuedRecordsMessage(
  String recordsSource
) {}
