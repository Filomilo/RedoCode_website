package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public abstract class Program {
  public abstract String getProgramCode();

  public abstract long getExecutionTimeLimitMs();

  public Variables.VARIABLES_TYPES getOutuputType() {
    return null;
  }
}
