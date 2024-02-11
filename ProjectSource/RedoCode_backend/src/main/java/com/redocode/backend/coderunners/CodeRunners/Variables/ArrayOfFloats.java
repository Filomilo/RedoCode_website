package com.redocode.backend.coderunners.CodeRunners.Variables;

public class ArrayOfFloats  extends Variables<Float[]>{
    public ArrayOfFloats(Float[] value) {
        super(value);
    }
    public ArrayOfFloats() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_OF_FLOATS;
    }

}
