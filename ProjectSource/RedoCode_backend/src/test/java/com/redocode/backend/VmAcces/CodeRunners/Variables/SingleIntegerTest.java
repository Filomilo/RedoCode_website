package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Islotating specific test for debugging")
class SingleIntegerTest {

  @Test
  void getType() {
    SingleInteger singleInteger = new SingleInteger();
    assertEquals(Variables.VARIABLES_TYPES.SINGLE_INTEGER, singleInteger.getType());
  }

  @Test
  void getValue() {
    SingleInteger singleInteger = new SingleInteger(123);
    assertEquals(123, singleInteger.getValue());
  }

  @Test
  void setValue() {
    SingleInteger singleInteger = new SingleInteger(123);
    singleInteger.setValue(321);
    assertEquals(321, singleInteger.getValue());
  }
}
