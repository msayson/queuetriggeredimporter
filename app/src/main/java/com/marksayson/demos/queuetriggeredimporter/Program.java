package com.marksayson.demos.queuetriggeredimporter;

import com.marksayson.demos.queuetriggeredimporter.domain.usecases.ingest.RecordsIngestor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryQueueReceiver;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryRecordsProcessor;
import com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways.InMemoryRecordsRetriever;

/**
 * Entry point for the program.
 */
public class Program {
  /**
   * Main function data record ingestion.
   * @param args Input arguments
   */
  public static void main(final String[] args) {
    System.out.println("Initializing ingestor...");
    final RecordsIngestor ingestor = instantiateIngestor();
    System.out.println("Pulling available records...");
    ingestor.ingestRecords();
    System.out.println("Ingestion complete, shutting down...");
  }

  private static RecordsIngestor instantiateIngestor() {
    return new RecordsIngestor(
      new InMemoryQueueReceiver(),
      new InMemoryRecordsRetriever(),
      new InMemoryRecordsProcessor()
    );
  }
}
