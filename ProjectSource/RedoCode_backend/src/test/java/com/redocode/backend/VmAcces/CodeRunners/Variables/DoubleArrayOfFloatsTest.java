package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class DoubleArrayOfFloatsTest {

    @Test
    void getType() {
        DoubleArrayOfFloats doubleArrayOfFloats= new DoubleArrayOfFloats();
        assertEquals(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS,doubleArrayOfFloats.getType());
    }

    @Test
    void getValue() {
        Float[][] arr = {
                {1.0f,2.0f,3.0f},
                {4.0f,5.0f,6.0f},
                {7.0f,8.0f,9.0f}
        };
        DoubleArrayOfFloats var= new DoubleArrayOfFloats(arr);
        assertEquals(arr,var.getValue());
    }

    @Test
    void setValue() {
        Float[][] arr = {
                {1.0f,2.0f,3.0f},
                {4.0f,5.0f,6.0f},
                {7.0f,8.0f,9.0f}
        };
        Float[][] arr2 = {
                {10.0f,11.0f,12.0f},
                {13.0f,14.0f,15.0f},
                {18.0f,17.0f,16.0f}
        };
        DoubleArrayOfFloats var= new DoubleArrayOfFloats(arr);
        var.setValue(arr2);
        assertEquals(arr2,var.getValue());
    }
}