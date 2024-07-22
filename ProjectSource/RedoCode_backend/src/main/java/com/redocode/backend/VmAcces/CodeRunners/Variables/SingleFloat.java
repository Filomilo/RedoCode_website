package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;


public class SingleFloat extends Variables<Float> {
    public SingleFloat(Float value) {
        super(value);
    }
    public SingleFloat(Double value) {
        super(value.floatValue());
    }
    public SingleFloat() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_FLOAT;
    }


    @Override
    public String toString() {
        return value.toString();
    }
}
