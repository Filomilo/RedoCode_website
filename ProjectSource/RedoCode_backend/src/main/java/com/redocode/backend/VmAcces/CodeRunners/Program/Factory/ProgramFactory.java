package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProgramFactory {


    public static RawProgramFactory createRawProgram() {
        return new RawProgramFactory();
    }

    public static SolutionProgramFactory createSolutionProgram() {
        return new SolutionProgramFactory();
    }


}
