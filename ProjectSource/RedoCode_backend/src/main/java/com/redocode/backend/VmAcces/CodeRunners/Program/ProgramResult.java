package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redocode.backend.VmAcces.CodeRunners.ConsoleOutput;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;

@Slf4j
@Builder
@Data
@AllArgsConstructor
@Value
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(force = true)
public class ProgramResult {
  @NonNull private ConsoleOutput consoleOutput;
  private Variables variables;
  private Variables variablesInput;

  @NotNull
  @Range(min = 0)
  private Long executionTime;
}
