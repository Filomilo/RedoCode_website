package com.redocode.backend.coderunners.CodeRunners.Variables;

public class SingleFloat extends Variables<Float>{
    public SingleFloat(Float value) {
        super(value);
    }
    public SingleFloat() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_FLOAT;
    }

}
