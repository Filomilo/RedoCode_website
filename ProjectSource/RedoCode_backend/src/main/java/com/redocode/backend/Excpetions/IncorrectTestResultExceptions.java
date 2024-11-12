package com.redocode.backend.Excpetions;

import com.redocode.backend.VmAcces.CodeRunners.Program.TestResults;
import lombok.Getter;

@Getter
public class IncorrectTestResultExceptions extends Exception {
  TestResults testResults;

  public IncorrectTestResultExceptions(TestResults testResults) {
    this.testResults = testResults;
  }
}
