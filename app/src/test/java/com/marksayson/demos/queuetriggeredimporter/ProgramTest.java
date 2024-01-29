package com.marksayson.demos.queuetriggeredimporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProgramTest {
  @Test void appHasAGreeting() {
    final Program classUnderTest = new Program();
    assertEquals("Hello world!", classUnderTest.getGreeting(), "Program did not have expected greeting");
  }

  @Test void testMainRunsSuccessfully() {
    final String[] testArgs = {"arg1"};
    Program.main(testArgs);
  }
}
