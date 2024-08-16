package com.redocode.backend.VmAcces.CodeRunners;

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

  protected CodeRunner(String image, int ramMb) {
    super(image, ramMb);
    type = CODE_RUNNER_TYPE.UNIDENTIFIED;
  }
@NotNull
  protected CODE_RUNNER_TYPE type;

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
    try {
      logger.info("running program: " + program + "--------" + program.getProgramCode());
      String fileName = createProgramCodeFile(program);
      String programName = compileProgram(fileName);
      String runCommand = getRunCommand(programName);
      ConsoleOutput consoleOutput = executeBash(runCommand, program.getExecutionTimeLimitMs());
      Variables programOutput = null;
      if (program.getOutuputType() != null) {
        String resultFileContent = getFileContnt(((SolutionProgram) program).getOutputFileName());
        logger.info("Program outoput file: \n" + resultFileContent);
        programOutput =
            VariablesParser.parseVaraiables(program.getOutuputType(), resultFileContent);
      }

      Variables inputVar = null;

      if (program instanceof SolutionProgram) {
        inputVar = ((SolutionProgram) program).getInput();
      }

      cleanup();
      return new ProgramResult(consoleOutput, programOutput, inputVar);
    } catch (Exception ex) {
      return ProgramResult.builder()
          .consoleOutput(ConsoleOutput.builder().output("").errorOutput(ex.getMessage()).build())
          .build();
    }
  }
}
