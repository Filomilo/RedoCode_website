package com.redocode.backend.coderunners.CodeRunners.Variables;

public class SingleInteger extends Variables<Integer> {
    public SingleInteger(Integer value) {
        super(value);
    }
    public SingleInteger() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
       return VARIABLES_TYPES.SINGLE_INTEGER;
    }


}
