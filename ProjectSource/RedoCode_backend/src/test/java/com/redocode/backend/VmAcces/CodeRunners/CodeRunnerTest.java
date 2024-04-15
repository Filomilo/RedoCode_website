package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayStringProvider")
    void runProgramCppReturnStringArray(String[] val) {
        log.info("Code runner cpp return");
        ArrayOfStrings resultTemplate=new ArrayOfStrings();
        resultTemplate.setW(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfStrings(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode("#include <string>\n" +
                        "std::string* solution(std::string* val, int length){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        assertArrayEquals((Object[]) val, (Object[]) result.getVariables().getValue());
    }


    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayIntProvider")
    void runProgramCppReturnIntArray(Integer[] val) {
        log.info("Code runner cpp return");
        ArrayOfIntegers resultTemplate=new ArrayOfIntegers();
        resultTemplate.setW(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfIntegers(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode(
                        "int* solution(int* val, int length){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        assertArrayEquals((Object[]) val, (Object[]) result.getVariables().getValue());
    }

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#arrayFloatProvider")
    void runProgramCppReturnIntArray(Float[] val) {
        log.info("Code runner cpp return");
        ArrayOfFloats resultTemplate=new ArrayOfFloats();
        resultTemplate.setW(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new ArrayOfFloats(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode(
                        "float* solution(float* val, int length){" +
                                "return val;" +
                                "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        for (int i = 0; i < val.length; i++) {
            assertTrue(Math.abs(val[i]-((Float[])result.getVariables().getValue())[i])<0.001);
        }
    }

    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayStringProvider")
    void runProgramCppReturnStringDoubleArray(String[][] val) {
        log.info("Code runner cpp return");
        DoubleArrayOfStrings resultTemplate=new DoubleArrayOfStrings();
        resultTemplate.setW(val[0].length);
        resultTemplate.setH(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfStrings(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode("#include <string>\n" +
                        "std::string** solution(std::string** val, int width, int height){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        for (int h = 0; h < val.length; h++) {
            for (int w = 0; w < val[0].length; w++) {
                assertEquals(val[h][w],((String[][])result.getVariables().getValue())[h][w]);
            }
        }
    }


    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayIntProvider")
    void runProgramCppReturnIntDoubleArray(Integer[][] val) {
        log.info("Code runner cpp return");
        DoubleArrayOfIntegers resultTemplate=new DoubleArrayOfIntegers();
        resultTemplate.setW(val[0].length);
        resultTemplate.setH(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfIntegers(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode(
                        "int** solution(int** val, int width, int height){" +
                        "return val;" +
                        "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        for (int h = 0; h < val.length; h++) {
            for (int w = 0; w < val[0].length; w++) {

                assertEquals(val[h][w],((Integer[][])result.getVariables().getValue())[h][w]);
            }
        }
    }



    @ParameterizedTest
    @MethodSource("com.redocode.backend.ValuesProvider#doubleArrayFloatProvider")
    void runProgramCppReturnFloatDoubleArray(Float[][] val) {
        log.info("Code runner cpp return");
        DoubleArrayOfFloats resultTemplate=new DoubleArrayOfFloats();
        resultTemplate.setW(val[0].length);
        resultTemplate.setH(val.length);
        Program program= ProgramFactory
                .createSolutionProgram()
                .setSolutionCodeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                .setInputVaraiable(new DoubleArrayOfFloats(val))
                .setOutputBase(resultTemplate)
                .setSolutionCode(
                        "float** solution(float** val, int width, int height){" +
                                "return val;" +
                                "}")
                .build();
        CodeRunner codeRunner= new CppCodeRunner();
        codeRunner.start();
        ProgramResult result= codeRunner.runProgram(program);
        codeRunner.destroy();
        log.info("\n\ncode result: \n"+ result);
        for (int h = 0; h < val.length; h++) {
            for (int w = 0; w < val[0].length; w++) {
                assertTrue(Math.abs(val[h][w]-((Float[][])result.getVariables().getValue())[h][w])<0.001);
            }
        }
    }


}