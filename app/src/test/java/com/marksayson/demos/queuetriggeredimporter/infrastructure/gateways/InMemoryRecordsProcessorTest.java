package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InMemoryRecordsProcessorTest {
  private InMemoryRecordsProcessor processor;

  @BeforeEach void setup() {
    processor = new InMemoryRecordsProcessor();
  }

  @Test void testProcessRecordsHandlesEmptyInput() {
    processor.processRecords(List.of());
  }
}
