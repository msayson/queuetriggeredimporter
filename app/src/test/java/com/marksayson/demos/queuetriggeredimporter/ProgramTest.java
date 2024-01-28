package com.marksayson.demos.queuetriggeredimporter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProgramTest {
  @Test void appHasAGreeting() {
    final Program classUnderTest = new Program();
    assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
  }
}
