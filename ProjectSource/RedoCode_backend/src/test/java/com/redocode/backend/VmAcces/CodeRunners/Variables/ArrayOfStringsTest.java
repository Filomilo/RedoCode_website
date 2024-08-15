package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Islotating specific test for debugging")
class ArrayOfStringsTest {

  @Test
  void getType() {
    ArrayOfStrings arrayOfStrings = new ArrayOfStrings();
    assertEquals(Variables.VARIABLES_TYPES.ARRAY_STRINGS, arrayOfStrings.getType());
  }

  @Test
  void getValue() {
    String[] arr = {"1", "2", "3", "4", "5", "6", "7"};
    ArrayOfStrings var = new ArrayOfStrings(arr);
    assertEquals(arr, var.getValue());
  }

  @Test
  void setValue() {
    String[] arr = {"1", "2", "3", "4", "5", "6", "7"};
    String[] arr2 = {"80", "90", "100", "110", "120", "130", "140"};
    ArrayOfStrings var = new ArrayOfStrings(arr);
    var.setValue(arr2);
    assertEquals(arr2, var.getValue());
  }

  @Test
  void printValue() {
    String[] arr = {"1", "2", "3", "4", "5", "6", "7"};
    ArrayOfStrings var = new ArrayOfStrings(arr);
    assertEquals("{\n1, 2, 3, 4, 5, 6, 7\n}", var.toString());
  }
}
