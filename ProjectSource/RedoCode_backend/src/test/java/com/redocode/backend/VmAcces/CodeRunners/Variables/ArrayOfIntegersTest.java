package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Islotating specific test for debugging")
class ArrayOfIntegersTest {

    @Test
    void getType() {
        ArrayOfIntegers arrayOfIntegers = new ArrayOfIntegers();
        assertEquals(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS, arrayOfIntegers.getType());
    }

    @Test
    void getValue() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayOfIntegers var = new ArrayOfIntegers(arr);
        assertEquals(arr, var.getValue());
    }

    @Test
    void setValue() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7};
        Integer[] arr2 = {80, 90, 100, 110, 120, 130, 140};
        ArrayOfIntegers var = new ArrayOfIntegers(arr);
        var.setValue(arr2);
        assertEquals(arr2, var.getValue());
    }

}