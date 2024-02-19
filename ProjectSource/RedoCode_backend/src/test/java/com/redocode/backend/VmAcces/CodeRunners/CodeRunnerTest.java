package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleInteger;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleString;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class CodeRunnerTest {

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleIntProvider")
    void runProgramCppReturnInt(Integer val) {
        log.info("Code runner cpp return");
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleInteger(val))
                .setOutputBase(new SingleInteger())
                .setSolutionCode("int solution(int val){" +
                        "return val;" +
                        "}")
                .build();

        log.info("Code to run: \n\n\n"+program.getProgramCode()+"\n\n\n" );


        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
       // codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        assertEquals(val,result.getVariables().getValue());
    }

    @Test
    void testRunProgram() {
    }

    @Test
    void testRunProgram1() {
    }
}