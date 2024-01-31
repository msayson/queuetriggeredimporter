package com.marksayson.demos.queuetriggeredimporter.domain.gateways;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;

import java.util.Collection;
import java.util.Optional;

/**
 * Gateway for retrieving records from a data source.
 */
public interface RecordsRetriever {
  Optional<Collection<DataRecord>> retrieveRecords(final String sourceLocation);
}
