package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.CppSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.JsSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//@Disabled("Islotating specific test for debugging")
class SolutionProgramFactoryTest {
    @Test
    void buildCpp() {
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setTimeout(100)
                .setInputVaraiable(new SingleInteger(1))
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_FLOAT)
                .setSolutionCode("")
                .build();
        assertSame(CppSolutionProgram.class,program.getClass());
    }
    @Test
    void buildJs() {
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                .setTimeout(100)
                .setInputVaraiable(new SingleInteger(1))
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_FLOAT)
                .setSolutionCode("")
                .build();
        assertSame(JsSolutionProgram.class,program.getClass());
    }

}
