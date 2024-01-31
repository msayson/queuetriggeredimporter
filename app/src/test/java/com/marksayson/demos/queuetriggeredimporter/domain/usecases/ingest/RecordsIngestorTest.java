package com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import com.marksayson.demos.queuetriggeredimporter.domain.entities.QueuedRecordsMessage;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryQueueReceiver;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryRecordsRetriever;

import java.util.List;

import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryRecordsProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecordsIngestorTest {
  private InMemoryQueueReceiver queueReceiver;
  private InMemoryRecordsRetriever recordsRetriever;
  private InMemoryRecordsProcessor recordsProcessor;
  private RecordsIngestor ingestor;

  private static QueuedRecordsMessage TEST_MESSAGE = new QueuedRecordsMessage("TestRecordSource");

  @BeforeEach void setup() {
    queueReceiver = new InMemoryQueueReceiver();
    recordsRetriever = new InMemoryRecordsRetriever();
    recordsProcessor = new InMemoryRecordsProcessor();

    ingestor = new RecordsIngestor(queueReceiver, recordsRetriever, recordsProcessor);
  }

  @Test void testIngestRecordsWithEmptyQueue() {
    ingestor.ingestRecords();
  }

  @Test void testIngestRecordsDeletesProcessedMessageFromQueue() {
    queueReceiver.addMessageToQueue(TEST_MESSAGE);

    ingestor.ingestRecords();
    assertTrue(queueReceiver.getMessageFromQueue().isEmpty());
  }

  @Test void testIngestRecordsWithEmptyData() {
    queueReceiver.addMessageToQueue(TEST_MESSAGE);
    recordsRetriever.uploadRecords(TEST_MESSAGE.recordsSource(), List.of());

    ingestor.ingestRecords();
  }

  @Test void testIngestRecordsWithAvailableData() {
    final DataRecord testDataRecord = new DataRecord(
      "id", "data", "2023-12-01T10:55:01Z", "2024-01-28T05:35:22Z"
    );
    queueReceiver.addMessageToQueue(TEST_MESSAGE);
    recordsRetriever.uploadRecords(
      TEST_MESSAGE.recordsSource(),
      List.of(testDataRecord, testDataRecord)
    );

    ingestor.ingestRecords();
  }
}
