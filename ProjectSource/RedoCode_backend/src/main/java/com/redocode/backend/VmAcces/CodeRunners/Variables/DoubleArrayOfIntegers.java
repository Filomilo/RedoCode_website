package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;


public class DoubleArrayOfIntegers extends Variables<Integer [][]> {
    public DoubleArrayOfIntegers(Integer [][] value) {
        super(value);
    }
    public DoubleArrayOfIntegers() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS;
    }

}
