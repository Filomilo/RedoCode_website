package com.redocode.backend.VmAcces.CodeRunners;

public class ConsoleOutput {

    public int exitCode;
    public String output;
    public String errorOutput;

    public ConsoleOutput(int exitCode, String output, String errorOutput) {
        this.exitCode = exitCode;
        this.output = output;
        this.errorOutput = errorOutput;
    }

    @Override
    public String toString() {
        return "ConsoleOutput{" +
                "exitCode=" + exitCode +
                ", output='" + output + '\'' +
                ", errorOutput='" + errorOutput + '\'' +
                '}';
    }
}
