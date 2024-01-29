package com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueReceiver;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsProcessor;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsRetrievor;

import java.util.Collection;
import java.util.Optional;

/**
 * Encapsulates logic for ingesting data based on queued data import messages.
 */
public class RecordsIngestor {
  private final QueueReceiver queueReceiver;
  private final RecordsRetrievor recordsRetrievor;
  private final RecordsProcessor recordsProcessor;

  /**
   * Construct instance of RecordsIngestor.
   * @param queueReceiver    Data import queue listener
   * @param recordsRetrievor Data record retrievor
   * @param recordsProcessor Data record processor
   */
  public RecordsIngestor(
    final QueueReceiver queueReceiver,
    final RecordsRetrievor recordsRetrievor,
    final RecordsProcessor recordsProcessor
  ) {
    this.queueReceiver = queueReceiver;
    this.recordsRetrievor = recordsRetrievor;
    this.recordsProcessor = recordsProcessor;
  }

  /**
   * Ingest records from the data source provided by the latest message in the queue.
   */
  public void ingestRecords() {
    final Optional<QueuedRecordsMessage> optionalMessage = queueReceiver.getMessageFromQueue();
    if (optionalMessage.isEmpty()) {
      return;
    }

    final QueuedRecordsMessage message = optionalMessage.get();
    final String recordsSource = message.recordsSource();
    final Optional<Collection<DataRecord>> optionalRecords = recordsRetrievor.retrieveRecords(recordsSource);
    if (optionalRecords.isPresent()) {
      recordsProcessor.processRecords(optionalRecords.get());
    }
    queueReceiver.deleteMessageFromQueue(message);
  }
}
