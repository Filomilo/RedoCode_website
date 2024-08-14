package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class VariablesFactory {


    public static Variables getVeraibleFromType(Variables.VARIABLES_TYPES type) {
        Variables variables = null;
        switch (type) {
            case SINGLE_INTEGER -> {
                variables = new SingleInteger();
            }
            case SINGLE_STRING -> {
                variables = new SingleString();
            }
            case SINGLE_FLOAT -> {
                variables = new SingleFloat();
            }
            case ARRAY_OF_INTEGERS -> {
                new ArrayOfStrings();
            }
            case ARRAY_STRINGS -> {
                new ArrayOfStrings();
            }
            case ARRAY_OF_FLOATS -> {
                new ArrayOfFloats();
            }
            case DOUBLE_ARRAY_OF_INTEGERS -> {
                new DoubleArrayOfIntegers();
            }
            case DOUBLE_ARRAY_OF_FLOATS -> {
                new DoubleArrayOfFloats();
            }
            case DOUBLE_ARRAY_OF_STRINGS -> {
                new DoubleArrayOfStrings();
            }
        }
        return variables;
    }


}
