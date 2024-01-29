package com.marksayson.demos.queuetriggeredimporter.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DataRecordTest {
  private static final String VALID_ID = "1";
  private static final String VALID_TITLE = "TestTitle";
  private static final String VALID_DATE_TIME = "2011-12-03T10:15:30Z";

  @Test void testAcceptsValidInput() {
    final DataRecord dataRecord = new DataRecord(VALID_ID, VALID_TITLE, VALID_DATE_TIME, VALID_DATE_TIME);
    assertNotNull(dataRecord);
  }

  @Test void testRejectsNullId() {
    final Exception exception = assertThrows(NullPointerException.class, () ->
      new DataRecord(null, VALID_TITLE, VALID_DATE_TIME, VALID_DATE_TIME));
    assertEquals(DataRecord.NULL_ID_MESSAGE, exception.getMessage());
  }

  @Test void testRejectsNullData() {
    final Exception exception = assertThrows(NullPointerException.class, () ->
      new DataRecord(VALID_ID, null, VALID_DATE_TIME, VALID_DATE_TIME));
    assertEquals(DataRecord.NULL_DATA_MESSAGE, exception.getMessage());
  }

  @Test void testRejectsNullCreatedAt() {
    final Exception exception = assertThrows(NullPointerException.class, () ->
      new DataRecord(VALID_ID, VALID_TITLE, null, VALID_DATE_TIME));
    assertEquals(DataRecord.NULL_CREATED_AT_MESSAGE, exception.getMessage());
  }

  @Test void testRejectsNullUpdatedAt() {
    final Exception exception = assertThrows(NullPointerException.class, () ->
      new DataRecord(VALID_ID, VALID_TITLE, VALID_DATE_TIME, null));
    assertEquals(DataRecord.NULL_UPDATED_AT_MESSAGE, exception.getMessage());
  }

  @Test void testRejectsNonDateTimeUpdatedAt() {
    final Exception exception = assertThrows(IllegalArgumentException.class, () ->
      new DataRecord(VALID_ID, VALID_TITLE, VALID_DATE_TIME, "2007-12-03"));
    assertEquals(DataRecord.NOT_ISO_8601_INSTANT_MESSAGE, exception.getMessage());
  }
}
