package com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions;

import com.redocode.backend.VmAcces.CodeRunners.CppCodeRunner;
import com.redocode.backend.VmAcces.CodeRunners.Program.CppSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

public class StartingFunctionGeneratorCpp implements StartingFunctionGenerator {
    @Override
    public String getStartingFunction(Variables.VARIABLES_TYPES inputType, Variables.VARIABLES_TYPES ouptuType) {
        String activationFunction=
         CppSolutionProgram.getVarName(ouptuType)+" solution "+"("+CppSolutionProgram.getVarName(inputType)+ " val)\n{\n\n}";
        return activationFunction;
    }
}
