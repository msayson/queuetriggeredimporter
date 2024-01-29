package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import com.marksayson.demos.queuetriggeredimporter.domain.gateways.RecordsProcessor;

import java.util.Collection;

/**
 * In-memory implementation of a records processor.
 */
public class InMemoryRecordsProcessor implements RecordsProcessor {

  @Override
  public void processRecords(final Collection<DataRecord> records) {
    return;
  }
}
