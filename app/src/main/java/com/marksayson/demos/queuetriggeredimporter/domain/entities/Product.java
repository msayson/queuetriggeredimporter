package com.marksayson.demos.queuetriggeredimporter.domain.entities;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A product data record.
 * @param id        Unique identifier
 * @param title     Title
 * @param createdAt Created timestamp in ISO 8601 UTC time, eg. 2024-01-28T05:35:22Z
 * @param updatedAt Updated timestamp in ISO 8601 UTC time
 * @see <a href="https://www.iso.org/iso-8601-date-and-time-format.html">ISO 8601</a>
 */
public record Product(
  String id,
  String title,
  String createdAt,
  String updatedAt
) {
  static final String NULL_ID_MESSAGE = "Must provide non-null id";
  static final String NULL_TITLE_MESSAGE = "Must provide non-null title";
  static final String NULL_CREATED_AT_MESSAGE = "Must provide non-null createdAt";
  static final String NULL_UPDATED_AT_MESSAGE = "Must provide non-null updatedAt";
  static final String NOT_ISO_8601_INSTANT_MESSAGE = "Must provide ISO 8601 instant";

  /**
   * Validate parameters before instantiating product.
   * @param id        ID
   * @param title     Title
   * @param createdAt Created at time
   * @param updatedAt Updated at time
   */
  public Product {
    Objects.requireNonNull(id, NULL_ID_MESSAGE);
    Objects.requireNonNull(title, NULL_TITLE_MESSAGE);
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
