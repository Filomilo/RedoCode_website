package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.ArrayOfFloats;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunnerSpecificSolutionFactoryTest {

    @Test
    void setInputVaraiable() {
        ArrayOfFloats inuputVar= new ArrayOfFloats(new Float[]{11.0f, 55.0f, 12.0f});

         SolutionProgram solutionProgram= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable((Variables) inuputVar)
                 .build();

         assertEquals(inuputVar,solutionProgram.getInput());
    }

    @Test
    void setOutputType() {
        SolutionProgram solutionProgram= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setOutputType(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS)
                .build();
        assertEquals(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS,solutionProgram.getOutput());
    }

    @Test
    void setSolutionCode() {

        String code="test code";

        SolutionProgram solutionProgram= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setSolutionCode(code)
                .build();
        assertEquals(code,solutionProgram.getSolutionCode());
    }

    @Test
    void build() {
    }
}