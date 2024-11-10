package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@SuperBuilder
@Slf4j
@AllArgsConstructor
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
@Setter
@Getter
public class TestResults extends ProgramResult {
  @Setter private Variables expectedRes = null;

  public TestResults(ProgramResult programResult) {
    this.setConsoleOutput(programResult.getConsoleOutput());
    this.setVariables(programResult.getVariables());
    this.setExecutionTime(programResult.getExecutionTime());
    this.setVariablesInput(programResult.getVariablesInput());
  }
}
