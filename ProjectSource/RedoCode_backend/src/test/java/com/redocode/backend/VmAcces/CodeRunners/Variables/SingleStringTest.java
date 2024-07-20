package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled("Islotating specific test for debugging")
class SingleStringTest {

    @Test
    void getType() {
        SingleString singleString= new SingleString();
        assertEquals(Variables.VARIABLES_TYPES.SINGLE_STRING,singleString.getType());
    }

    @Test
    void getValue() {
        SingleString var= new SingleString("123");
        assertEquals("123",var.getValue());
    }

    @Test
    void setValue() {
        SingleString var= new SingleString("123");
        var.setValue("321");
        assertEquals("321",var.getValue());
    }

}