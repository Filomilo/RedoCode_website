package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class SingleInteger extends Variables<Integer> {
    public SingleInteger(Integer value) {
        super(value);
    }
    public SingleInteger() {
        super();
    }
    @Override
    public VARIABLES_TYPES getType() {
       return VARIABLES_TYPES.SINGLE_INTEGER;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
