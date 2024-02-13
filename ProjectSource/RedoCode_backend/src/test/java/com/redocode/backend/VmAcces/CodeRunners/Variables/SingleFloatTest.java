package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleFloatTest {

    @Test
    void getType() {
        SingleFloat singleFloat= new SingleFloat();
        assertEquals(Variables.VARIABLES_TYPES.SINGLE_FLOAT,singleFloat.getType());
    }

    @Test
    void getValue() {
        SingleFloat var= new SingleFloat(123.123f);
        assertEquals(123.123f,var.getValue());
    }

    @Test
    void setValue() {
        SingleFloat var= new SingleFloat(123.123f);
        var.setValue(321.321f);
        assertEquals(321.321f,var.getValue());
    }

}