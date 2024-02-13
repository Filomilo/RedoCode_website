package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class ArrayOfIntegers  extends Variables<Integer[]> {
    public ArrayOfIntegers(Integer[] value) {
        super(value);
    }
    public ArrayOfIntegers() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_OF_INTEGERS;
    }

}
