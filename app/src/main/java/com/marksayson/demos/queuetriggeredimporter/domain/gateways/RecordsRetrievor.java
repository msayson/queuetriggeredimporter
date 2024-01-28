package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;

import java.util.Collection;

/**
 * Gateway for retrieving records from a data source.
 */
public interface RecordsRetrievor {
  Collection<DataRecord> retrieveRecords(final String source);
}