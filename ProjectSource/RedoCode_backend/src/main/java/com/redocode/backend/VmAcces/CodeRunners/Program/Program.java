package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

abstract public class Program {
    abstract public String getProgramCode();

    public  Variables.VARIABLES_TYPES getOutuputType() {
        return null;
    }
}
