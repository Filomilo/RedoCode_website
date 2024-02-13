package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

abstract public class Program {
    abstract public String getProgramCode();

    Variables.VARIABLES_TYPES programOutputType=null;

    public  Variables.VARIABLES_TYPES getOutuputType() {
        return programOutputType;
    }
}
