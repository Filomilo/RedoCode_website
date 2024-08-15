package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.UUID;

public class CppCodeRunner extends CodeRunner {

  public CppCodeRunner(int ramMb) {
    super("filipredocode/redocode:CppRunner", ramMb);
    type = CODE_RUNNER_TYPE.CPP_RUNNER;
  }

  @Override
  String createProgramCodeFile(Program program) {
    String fileName = UUID.randomUUID().toString();
    fileName += ".cpp";
    createFile(fileName, program.getProgramCode());
    return fileName;
  }

  @Override
  @SneakyThrows
  String compileProgram(String sourceFile) {
    String fileName = UUID.randomUUID().toString();
    String compileCommand = "g++ -Wall " + sourceFile + " -o " + fileName;
    ConsoleOutput consoleOutput = executeBash(compileCommand, 5000);
    logger.info("Compilation result: " + consoleOutput.toString());
    if (consoleOutput.getErrorOutput().length() > 0)
      throw new RuntimeException(consoleOutput.getErrorOutput());
    return fileName;
  }

  @Override
  String getRunCommand(String programFile) {
    logger.info("List of files: \n" + Arrays.toString(listFiles()));
    return "./" + programFile;
  }
}
