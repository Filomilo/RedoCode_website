package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class RunnerSpecificSolutionFactory {

  private SolutionProgram solutionProgram;

  public RunnerSpecificSolutionFactory setInputVaraiable(Variables var) {
    solutionProgram.setInput(var);
    return this;
  }

  public RunnerSpecificSolutionFactory setOutputBase(Variables.VARIABLES_TYPES varType) {
    solutionProgram.setOutputType(varType);
    return this;
  }

  public RunnerSpecificSolutionFactory setSolutionCode(String code) {
    solutionProgram.setSolutionCode(code);
    return this;
  }

  public RunnerSpecificSolutionFactory setTimeout(long timeoout) {
    solutionProgram.setExecutionTimeLimitMs(timeoout);
    return this;
  }

  @SneakyThrows
  public SolutionProgram build() {
    if (solutionProgram.getInput() == null
        || solutionProgram.getOutputType() == null
        || solutionProgram.getSolutionCode() == null
        || solutionProgram.getExecutionTimeLimitMs() < 0)
      throw new Exception("All paramters should be set");
    return solutionProgram;
  }
}
