package com.marksayson.demos.queuetriggeredimporter.domain.entities;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A normalized record.
 * @param id        Unique identifier
 * @param data      Record data
 * @param createdAt Record created timestamp in ISO 8601 UTC time, eg. 2024-01-28T05:35:22Z
 * @param updatedAt Record updated timestamp in ISO 8601 UTC time
 * @see <a href="https://www.iso.org/iso-8601-date-and-time-format.html">ISO 8601</a>
 */
public record DataRecord(
  String id,
  String data,
  String createdAt,
  String updatedAt
) {
  static final String NULL_ID_MESSAGE = "Must provide non-null id";
  static final String NULL_DATA_MESSAGE = "Must provide non-null data";
  static final String NULL_CREATED_AT_MESSAGE = "Must provide non-null createdAt";
  static final String NULL_UPDATED_AT_MESSAGE = "Must provide non-null updatedAt";
  static final String NOT_ISO_8601_INSTANT_MESSAGE = "Must provide ISO 8601 instant";

  /**
   * Validate parameters before instantiating record.
   * @param id        Record ID
   * @param data      Record data
   * @param createdAt Created at time
   * @param updatedAt Updated at time
   */
  public DataRecord {
    Objects.requireNonNull(id, NULL_ID_MESSAGE);
    Objects.requireNonNull(data, NULL_DATA_MESSAGE);
    Objects.requireNonNull(createdAt, NULL_CREATED_AT_MESSAGE);
    Objects.requireNonNull(updatedAt, NULL_UPDATED_AT_MESSAGE);

    validateIsUTCDateTime(createdAt);
    validateIsUTCDateTime(updatedAt);
  }

  private void validateIsUTCDateTime(final String dateTimeStr) {
    try {
      Instant.parse(dateTimeStr);
    } catch (final DateTimeParseException exception) {
      throw new IllegalArgumentException(NOT_ISO_8601_INSTANT_MESSAGE);
    }
  }
}
