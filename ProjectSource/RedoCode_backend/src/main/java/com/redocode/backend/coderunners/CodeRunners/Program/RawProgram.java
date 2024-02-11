package com.redocode.backend.coderunners.CodeRunners.Program;

public class RawProgram extends Program {

    String Code="";

    @Override
    String getProgramCode() {
        return Code;
    }

    void setCode(String code)
    {
        this.Code=code;
    }
}
