package com.redocode.backend.VmAcces.CodeRunners.Variables;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class SingleInteger extends Variables<Integer> {
    public SingleInteger(Integer value) {
        super(value);
    }
    public SingleInteger() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
       return VARIABLES_TYPES.SINGLE_INTEGER;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
