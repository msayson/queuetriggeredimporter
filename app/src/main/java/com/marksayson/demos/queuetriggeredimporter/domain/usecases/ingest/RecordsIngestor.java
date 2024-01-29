package com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.QueueReceiver;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsProcessor;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsRetrievor;
import org.immutables.value.Value;

import java.util.Collection;
import java.util.Optional;

/**
 * Encapsulates logic for ingesting data based on queued data import messages.
 */
@Value.Immutable
public abstract class RecordsIngestor {
  abstract QueueReceiver getQueueReceiver();

  abstract RecordsRetrievor getRecordsRetrievor();

  abstract RecordsProcessor getRecordsProcessor();

  /**
   * Ingest records from the data source provided by the latest message in the queue.
   */
  public void ingestRecords() {
    final Optional<QueuedRecordsMessage> optionalMessage = getQueueReceiver().getMessageFromQueue();
    if (optionalMessage.isEmpty()) {
      return;
    }

    final String recordsSource = optionalMessage.get().recordsSource();
    final Optional<Collection<DataRecord>> optionalRecords = getRecordsRetrievor().retrieveRecords(recordsSource);
    if (optionalRecords.isPresent()) {
      getRecordsProcessor().processRecords(optionalRecords.get());
    }
  }
}
