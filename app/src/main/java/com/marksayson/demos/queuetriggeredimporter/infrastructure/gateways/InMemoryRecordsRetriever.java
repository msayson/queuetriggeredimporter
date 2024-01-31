package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsRetriever;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of a records retriever.
 */
public class InMemoryRecordsRetriever implements RecordsRetriever {

  private final Map<String, Collection<DataRecord>> dataProvider;

  public InMemoryRecordsRetriever() {
    dataProvider = new HashMap<>();
  }

  public void uploadRecords(final String sourceLocation, final Collection<DataRecord> records) {
    dataProvider.put(sourceLocation, records);
  }

  @Override
  public Optional<Collection<DataRecord>> retrieveRecords(final String sourceLocation) {
    final Collection<DataRecord> nullableMatchingRecords = dataProvider.get(sourceLocation);
    return Optional.ofNullable(nullableMatchingRecords);
  }
}
