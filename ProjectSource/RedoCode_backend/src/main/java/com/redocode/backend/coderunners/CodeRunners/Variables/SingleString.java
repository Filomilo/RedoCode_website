package com.redocode.backend.coderunners.CodeRunners.Variables;

public class SingleString  extends Variables<String> {
    public SingleString(String value) {
        super(value);
    }
    public SingleString() {
        super();
    }
    @Override
    VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_STRING;
    }

}
