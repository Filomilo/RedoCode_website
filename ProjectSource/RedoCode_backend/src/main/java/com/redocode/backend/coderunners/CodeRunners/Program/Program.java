package com.redocode.backend.coderunners.CodeRunners.Program;

import com.redocode.backend.coderunners.CodeRunners.Variables.Variables;

abstract public class Program {
    abstract public String getProgramCode();

    Variables.VARIABLES_TYPES programOutputType=null;

    public  Variables.VARIABLES_TYPES getOutuputType() {
        return programOutputType;
    }
}
