package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class DoubleArrayOfIntegers extends Variables<Integer [][]> {
    public DoubleArrayOfIntegers(Integer [][] value) {
        super(value);
    }
    public DoubleArrayOfIntegers() {
        super();
    }
    @Override
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS;
    }

}
