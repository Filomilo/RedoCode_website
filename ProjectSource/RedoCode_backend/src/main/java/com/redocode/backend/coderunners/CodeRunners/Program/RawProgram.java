package com.redocode.backend.coderunners.CodeRunners.Program;

public class RawProgram extends Program {

    String code="";

    public RawProgram(String code) {
        super();
        this.code=code;
    }

    @Override
    public String getProgramCode() {
        return code;
    }

    void setCode(String code)
    {
        this.code=code;
    }
}
