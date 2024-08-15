package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.CppSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.JsSolutionProgram;
import com.redocode.backend.VmAcces.CodeRunners.Program.SolutionProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SolutionProgramFactory {

    private SolutionProgram solutionProgram;

   public RunnerSpecificSolutionFactory setSolutionCodeRunner(CODE_RUNNER_TYPE type) {
        this.solutionProgram=switch (type){
            case CPP_RUNNER -> new CppSolutionProgram();
            case JS_RUNNER -> new JsSolutionProgram();
            default -> new CppSolutionProgram();
        };
        //todo: add exception when unedetided s code runenr request
        return new RunnerSpecificSolutionFactory(solutionProgram);
    }

}
