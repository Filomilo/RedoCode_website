package com.redocode.backend.coderunners.CodeRunners.Variables;

public class DoubleArrayOfIntegers extends Variables<Integer [][]> {
    public DoubleArrayOfIntegers(Integer [][] value) {
        super(value);
    }
    public DoubleArrayOfIntegers() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS;
    }

}
