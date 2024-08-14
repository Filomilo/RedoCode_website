package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Islotating specific test for debugging")
class DoubleArrayOfIntegersTest {

    @Test
    void getType() {
        DoubleArrayOfIntegers doubleArrayOfIntegers = new DoubleArrayOfIntegers();
        assertEquals(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS, doubleArrayOfIntegers.getType());
    }

    @Test
    void getValue() {
        Integer[][] arr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        DoubleArrayOfIntegers var = new DoubleArrayOfIntegers(arr);
        assertEquals(arr, var.getValue());
    }

    @Test
    void setValue() {
        Integer[][] arr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Integer[][] arr2 = {
                {10, 11, 12},
                {13, 14, 15},
                {18, 17, 16}
        };
        DoubleArrayOfIntegers var = new DoubleArrayOfIntegers(arr);
        var.setValue(arr2);
        assertEquals(arr2, var.getValue());
    }
}