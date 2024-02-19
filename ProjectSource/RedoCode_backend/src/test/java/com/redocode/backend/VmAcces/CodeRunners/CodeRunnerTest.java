package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleFloat;
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
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        assertEquals(val,result.getVariables().getValue());
    }

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleFloatProvider")
    void runProgramCppReturnFloat(Float val) {
        log.info("Code runner cpp return");
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleFloat(val))
                .setOutputBase(new SingleFloat())
                .setSolutionCode("float solution(float val){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        assertEquals(val,result.getVariables().getValue());
    }

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#singleStringProvider")
    void runProgramCppReturnString(String val) {
        log.info("Code runner cpp return");
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new SingleString(val))
                .setOutputBase(new SingleString())
                .setSolutionCode("#include <string>\n" +
                        "std::string solution(std::string val){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
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