package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class SingleFloat extends Variables<Float> {
    public SingleFloat(Float value) {
        super(value);
    }
    public SingleFloat() {
        super();
    }
    @Override
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_FLOAT;
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
