package com.marksayson.demos.queuetriggeredimporter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramTest {
  @Test void appHasAGreeting() {
    final Program classUnderTest = new Program();
    assertEquals("Hello world!", classUnderTest.getGreeting(), "Program did not have expected greeting");
  }
}
