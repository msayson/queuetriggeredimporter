package com.marksayson.demos.queuetriggeredimporter.domain.entities;

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
) {}
