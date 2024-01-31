package com.marksayson.demos.queuetriggeredimporter.infrastructure.gateways;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InMemoryProductsProcessorTest {
  private InMemoryProductsProcessor processor;

  @BeforeEach void setup() {
    processor = new InMemoryProductsProcessor();
  }

  @Test void testProcessProductsHandlesEmptyInput() {
    processor.processProducts(List.of());
  }
}
