package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;

import java.util.Collection;

/**
 * Gateway for processing imported records.
 */
public interface RecordsProcessor {
  void processRecords(final Collection<DataRecord> records);
}
