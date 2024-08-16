package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Islotating specific test for debugging")
class ArrayOfFloatsTest {

  @Test
  void getType() {
    ArrayOfFloats arrayOfFloats = new ArrayOfFloats();
    assertEquals(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS, arrayOfFloats.getType());
  }

  @Test
  void getValue() {
    Float[] arr = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f};
    ArrayOfFloats var = new ArrayOfFloats(arr);
    assertEquals(arr, var.getValue());
  }

  @Test
  void setValue() {
    Float[] arr = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f};
    Float[] arr2 = {80.0f, 90.0f, 100.0f, 110.0f, 120.0f, 130.0f, 140.0f};
    ArrayOfFloats var = new ArrayOfFloats(arr);
    var.setValue(arr2);
    assertEquals(arr2, var.getValue());
  }
}
