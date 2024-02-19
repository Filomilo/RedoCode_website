package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.redocode.backend.Tools.StringFormatter;

public class VariablesParser {
    static public Variables parseVaraiables(Variables.VARIABLES_TYPES type, String toParse)
    {
        Variables var= switch (type)
        {

            case SINGLE_INTEGER -> new SingleInteger(Integer.parseInt(toParse));
            case SINGLE_STRING -> new SingleString(StringFormatter.recoverWhiteCharacter(toParse));
            case SINGLE_FLOAT -> new SingleFloat(Float.parseFloat(toParse));
            case ARRAY_OF_INTEGERS -> null;
            case ARRAY_STRINGS -> null;
            case ARRAY_OF_FLOATS -> null;
            case DOUBLE_ARRAY_OF_INTEGERS -> null;
            case DOUBLE_ARRAY_OF_FLOATS -> null;
            case DOUBLE_ARRAY_OF_STRINGS -> null;
        };
        return var;
    }
    
    
    
}
