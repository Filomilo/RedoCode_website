package com.redocode.backend.VmAcces.CodeRunners.Variables;

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
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_OF_FLOATS;
    }

}
