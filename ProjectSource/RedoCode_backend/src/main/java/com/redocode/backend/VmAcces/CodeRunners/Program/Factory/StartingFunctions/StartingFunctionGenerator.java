package com.redocode.backend.VmAcces.CodeRunners.Program.Factory.StartingFunctions;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;

public interface StartingFunctionGenerator {
  public String getStartingFunction(
      Variables.VARIABLES_TYPES inputType, Variables.VARIABLES_TYPES ouptuType);
}
