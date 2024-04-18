package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.jackson.Jacksonized;


public class ArrayOfStrings  extends Variables<String[]> {
    public ArrayOfStrings(String[] value) {
        super(value);
    }
    public ArrayOfStrings() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.ARRAY_STRINGS;
    }

}
