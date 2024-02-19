package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.redocode.backend.Tools.StringFormatter;

public class SingleString  extends Variables<String> {
    public SingleString(String value) {
        super(value);
    }
    public SingleString() {
        super();
    }
    @Override
    public VARIABLES_TYPES getType() {
        return VARIABLES_TYPES.SINGLE_STRING;
    }

    @Override
    public String toString() {
        return "\"" + StringFormatter.removeWhiteCharacterss(value) +
                '\"';
    }
}
