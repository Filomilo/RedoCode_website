package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class DoubleArrayOfStringsTest {

    @Test
    void getType() {
        DoubleArrayOfStrings doubleArrayOfStrings= new DoubleArrayOfStrings();
        assertEquals(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS,doubleArrayOfStrings.getType());
    }

    @Test
    void getValue() {
        String[][] arr = {
                {"x1y1", "x2y1", "x3y1"},
                {"x1y2", "x2y2", "x3y2"},
                {"x1y3", "x2y3", "x3y3"}
        };

        DoubleArrayOfStrings var= new DoubleArrayOfStrings(arr);
        assertEquals(arr,var.getValue());
    }

    @Test
    void setValue() {
        String[][] arr = {
                {"x1y1", "x2y1", "x3y1"},
                {"x1y2", "x2y2", "x3y2"},
                {"x1y3", "x2y3", "x3y3"}
        };
        String[][] arr2 = {
                {"u1v1", "u2v1", "u2v1"},
                {"u1v2", "u2v2", "u2v2"},
                {"u1v3", "u2v3", "u2v3"}
        };
        DoubleArrayOfStrings var= new DoubleArrayOfStrings(arr);
        var.setValue(arr2);
        assertEquals(arr2,var.getValue());
    }


    @Test
    void printValue()
    {
        String[][] arr = {
                {"x1y1", "x2y1", "x3y1"},
                {"x1y2", "x2y2", "x3y2"},
                {"x1y3", "x2y3", "x3y3"}
        };
        DoubleArrayOfStrings var= new DoubleArrayOfStrings(arr);
        assertEquals("{\nx1y1, x2y1, x3y1\n" +
                "x1y2, x2y2, x3y2\n" +
                "x1y3, x2y3, x3y3\n}",var.toString());
    }
}