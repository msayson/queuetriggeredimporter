package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marksayson.demos.queuetriggeredimporter.domain.entities.DataRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class InMemoryRecordsRetrievorTest {
  private InMemoryRecordsRetrievor retrievor;

  private static final String TEST_RECORD_SOURCE_LOCATION = "TestSourceDataLocation";
  private static final String VALID_DATETIME = "2011-12-03T10:15:30Z";
  private static final Collection<DataRecord> TEST_RECORDS = List.of(
    new DataRecord("1", "title1", VALID_DATETIME, VALID_DATETIME),
    new DataRecord("2", "title2", VALID_DATETIME, VALID_DATETIME)
  );

  @BeforeEach void setup() {
    retrievor = new InMemoryRecordsRetrievor();
  }

  @Test void testRetrieveNonExistingRecords() {
    final String locationWithoutRecords = "NoRecordsHere";
    assertTrue(retrievor.retrieveRecords(locationWithoutRecords).isEmpty());

    retrievor.uploadRecords(TEST_RECORD_SOURCE_LOCATION, TEST_RECORDS);
    assertTrue(retrievor.retrieveRecords(locationWithoutRecords).isEmpty());
  }

  @Test void testRetrieveExistingRecords() {
    retrievor.uploadRecords(TEST_RECORD_SOURCE_LOCATION, TEST_RECORDS);

    final Optional<Collection<DataRecord>> records = retrievor.retrieveRecords(TEST_RECORD_SOURCE_LOCATION);
    assertTrue(records.isPresent());
    assertEquals(TEST_RECORDS, records.get());
  }
}
