package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;


public class DoubleArrayOfFloats  extends Variables<Float[][]> {
    public DoubleArrayOfFloats(Float[][] value) {
        super(value);
    }
    public DoubleArrayOfFloats() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS;
    }

}
