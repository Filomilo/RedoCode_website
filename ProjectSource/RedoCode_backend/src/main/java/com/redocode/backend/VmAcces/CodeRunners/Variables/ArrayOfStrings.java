package com.redocode.backend.VmAcces.CodeRunners.Variables;

public class ArrayOfStrings  extends Variables<String[]> {
    public ArrayOfStrings(String[] value) {
        super(value);
    }
    public ArrayOfStrings() {
        super();
    }
    @Override
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_STRINGS;
    }

}
