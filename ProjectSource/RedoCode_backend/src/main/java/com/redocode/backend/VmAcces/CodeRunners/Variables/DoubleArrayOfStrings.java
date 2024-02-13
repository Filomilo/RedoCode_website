package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class DoubleArrayOfStrings extends Variables<String[][]> {
    public DoubleArrayOfStrings(String[][] value) {
        super(value);
    }
    public DoubleArrayOfStrings() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS;
    }

}
