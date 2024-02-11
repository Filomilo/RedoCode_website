package com.redocode.backend.coderunners.CodeRunners.Variables;

public class DoubleArrayOfFloats  extends Variables<Float[][]> {
    public DoubleArrayOfFloats(Float[][] value) {
        super(value);
    }
    public DoubleArrayOfFloats() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS;
    }

}
