package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.CppSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.JsSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class SolutionProgramFactoryTest {
    @Test
    void buildCpp() {
        Program program= ProgramFactory.createSolutionProgram().setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER).build();
        assertSame(CppSolutionProgram.class,program.getClass());
    }
    @Test
    void buildJs() {
        Program program= ProgramFactory.createSolutionProgram().setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER).build();
        assertSame(JsSolutionProgram.class,program.getClass());
    }

}
