package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;

public class RawProgramFactory {
    private RawProgram rawProgram;


    RawProgramFactory setRawProgramCode(String code)
    {
        this.rawProgram= new RawProgram(code);
        return this;
    }


    RawProgram build()
    {
        if(this.rawProgram==null)
        {
            throw  new RuntimeException("Raw program needs to first be initlized");
        }
        return rawProgram;
    }

}
