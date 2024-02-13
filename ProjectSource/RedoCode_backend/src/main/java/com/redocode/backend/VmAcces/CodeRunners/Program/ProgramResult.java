package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

public class ProgramResult {
    public ConsoleOutput consoleOutput;
    public Variables variables;

    public ProgramResult(ConsoleOutput consoleOutput, Variables variables) {
        this.consoleOutput = consoleOutput;
        this.variables = variables;
    }

    public ProgramResult(ConsoleOutput consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    @Override
    public String toString() {
        return "CodeResult{" +
                "consoleOutput=" + consoleOutput +
                ", variables=" + variables +
                '}';
    }
}
