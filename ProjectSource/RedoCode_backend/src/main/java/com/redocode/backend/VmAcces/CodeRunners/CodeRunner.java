package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.Excpetions.ContainerException;
import com.redocode.backend.Excpetions.VmControllerException;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.VmAcces.CodeRunners.Variables.VariablesParser;
import lombok.Getter;
import lombok.Synchronized;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class CodeRunner extends ContainerController {

  protected CodeRunner(String image, int ramMb) throws ContainerException, VmControllerException {
    super(image, ramMb);
    type = CODE_RUNNER_TYPE.UNIDENTIFIED;
  }

  @NotNull protected CODE_RUNNER_TYPE type;

  abstract String createProgramCodeFile(Program program);

  abstract String compileProgram(String sourceFile);

  abstract String getRunCommand(String programFile);

  void cleanup() {
    String[] files = listFiles();
    for (String file : files) {
      removeFile(file);
    }
  }

  @Synchronized
  public ProgramResult runProgram(Program program) {
    long executionTime = -1;
    Variables inputVar = null;
    Variables outputVar = null;
    try {
      logger.info("running program: " + program + "--------" + program.getProgramCode());
      String fileName = createProgramCodeFile(program);
      String programName = compileProgram(fileName);
      String runCommand = getRunCommand(programName);
      if (program instanceof SolutionProgram) {
        inputVar = ((SolutionProgram) program).getInput();
      }

      long start = System.currentTimeMillis();
      ConsoleOutput consoleOutput =
          executeBash(runCommand, program.getExecutionTimeLimitMs() + 500);
      executionTime = System.currentTimeMillis() - start;
      if (executionTime > program.getExecutionTimeLimitMs()) {
        throw new Exception("Execution timeout exceeded: " + executionTime + " ms");
      }

      if (program.getOutuputType() != null) {
        String resultFileContent = getFileContnt(((SolutionProgram) program).getOutputFileName());
        logger.info("Program outoput file: \n" + resultFileContent);
        outputVar = VariablesParser.parseVaraiables(program.getOutuputType(), resultFileContent);
      }

      cleanup();
      return new ProgramResult(consoleOutput, outputVar, inputVar, executionTime);
    } catch (Exception ex) {
      return ProgramResult.builder()
          .consoleOutput(ConsoleOutput.builder().output("").errorOutput(ex.getMessage()).build())
          .variablesInput(inputVar)
          .variables(outputVar)
          .executionTime(executionTime)
          .build();
    }
  }
}
