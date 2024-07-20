package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
@NoArgsConstructor
@SuperBuilder
abstract public class Program {
    abstract public String getProgramCode();
    abstract public long getExecutionTimeLimitMs();
    public  Variables.VARIABLES_TYPES getOutuputType() {
        return null;
    }
}
