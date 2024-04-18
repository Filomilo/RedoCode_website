package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;


public class ArrayOfFloats  extends Variables<Float[]> {
    public ArrayOfFloats(Float[] value) {
        super(value);
    }
    public ArrayOfFloats() {
        super();
    }



    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_OF_FLOATS;
    }

}
