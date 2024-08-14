package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.ArrayOfFloats;
import com.redocode.backend.VmAcces.CodeRunners.Variables.DoubleArrayOfStrings;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Islotating specific test for debugging")
class RunnerSpecificSolutionFactoryTest {

    @Test
    void setInputVaraiable() {
        ArrayOfFloats inuputVar = new ArrayOfFloats(new Float[]{11.0f, 55.0f, 12.0f});

        SolutionProgram solutionProgram = ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable((Variables) inuputVar)
                .setTimeout(500)
                .setSolutionCode("")
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .build();

        assertEquals(inuputVar, solutionProgram.getInput());
    }

    @Test
    void setOutputType() {
        SolutionProgram solutionProgram = ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setOutputBase(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS)
                .setTimeout(500)
                .setSolutionCode("")
                .setInputVaraiable(new SingleInteger(1))
                .build();
        assertEquals(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS, solutionProgram.getOutputType());
    }

    @Test
    void setSolutionCode() {

        String code = "test code";

        SolutionProgram solutionProgram = ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setSolutionCode(code)
                .setTimeout(500)
                .setInputVaraiable(new SingleInteger(1))
                .setOutputBase(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .build();
        assertEquals(code, solutionProgram.getSolutionCode());
    }

    @Test
    void build() {
    }
}