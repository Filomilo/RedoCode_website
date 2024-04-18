package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redocode.backend.Tools.StringFormatter;
import lombok.extern.jackson.Jacksonized;

public class SingleString  extends Variables<String> {
    public SingleString(String value) {
        super(value);
    }
    public SingleString() {
        super();
    }
    @Override
    @JsonIgnore
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_STRING;
    }

    @Override
    public String toString() {
        return "\"" + StringFormatter.removeWhiteCharacterss(value) +
                '\"';
    }
}
