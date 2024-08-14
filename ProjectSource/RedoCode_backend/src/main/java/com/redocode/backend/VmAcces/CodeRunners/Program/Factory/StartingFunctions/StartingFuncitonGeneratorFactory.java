package com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions;

public class StartingFuncitonGeneratorFactory {
    public static StartingFunctionGenerator getStartingFunctionGeneratorForLanguage(String langaugeString) {
        switch (langaugeString) {
            case "cpp" -> {
                return new StartingFunctionGeneratorCpp();
            }
            case "js" -> {
                return new StartingFunctionGeneratorJs();
            }
        }
        return null;
    }
}
