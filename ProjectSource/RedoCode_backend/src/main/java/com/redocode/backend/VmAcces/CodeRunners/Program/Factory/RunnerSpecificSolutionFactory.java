package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RunnerSpecificSolutionFactory {

    private SolutionProgram solutionProgram;

    public RunnerSpecificSolutionFactory setInputVaraiable(Variables var)
    {
        solutionProgram.setInput(var);
        return this;
    }


    public RunnerSpecificSolutionFactory setOutputType(Variables.VARIABLES_TYPES varType)
    {
        solutionProgram.setOutput(varType);
        return this;
    }


    public RunnerSpecificSolutionFactory setSolutionCode(String code)
    {
        solutionProgram.setSolutionCode(code);
        return this;
    }


    public SolutionProgram build() {
        return solutionProgram;
    }
}
