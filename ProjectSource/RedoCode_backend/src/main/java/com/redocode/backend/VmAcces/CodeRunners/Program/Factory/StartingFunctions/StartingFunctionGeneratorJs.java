package com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

public class StartingFunctionGeneratorJs implements StartingFunctionGenerator {
  @Override
  public String getStartingFunction(
      Variables.VARIABLES_TYPES inputType, Variables.VARIABLES_TYPES ouptuType) {
    return "function solution (value){\n // your code\n}";
  }
}
